package app.aplication.sgd.ui.theme.app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.aplication.sgd.ui.theme.app.model.RegisterRequest
import app.aplication.sgd.ui.theme.app.model.RetrofitClient
import app.aplication.sgd.ui.theme.app.model.UserStatusResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val api = RetrofitClient.makeRetrofitService()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _userStatus = MutableStateFlow<UserStatusResponse?>(null)
    val userStatus: StateFlow<UserStatusResponse?> = _userStatus.asStateFlow()

    private val _pendingUsers = MutableStateFlow<List<UserStatusResponse>>(emptyList())
    val pendingUsers: StateFlow<List<UserStatusResponse>> = _pendingUsers.asStateFlow()

    private val _allUsers = MutableStateFlow<List<UserStatusResponse>>(emptyList())
    val allUsers: StateFlow<List<UserStatusResponse>> = _allUsers.asStateFlow()

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        val user = auth.currentUser
        if (user != null) {
            checkUserStatus(user.uid)
        } else {
            _authState.value = AuthState.NotAuthenticated
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid
                if (uid != null) {
                    checkUserStatus(uid)
                } else {
                    _authState.value = AuthState.Error("Error al iniciar sesión")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign in error", e)
                _authState.value = AuthState.Error(parseFirebaseError(e.message ?: ""))
            }
        }
    }

    fun signUp(email: String, password: String, nombre: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid
                if (uid != null) {
                    // Register in backend
                    val response = api.registerUser(
                        RegisterRequest(firebaseUid = uid, email = email, nombre = nombre)
                    )
                    _userStatus.value = response
                    _authState.value = AuthState.Pending
                } else {
                    _authState.value = AuthState.Error("Error al crear la cuenta")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign up error", e)
                _authState.value = AuthState.Error(parseFirebaseError(e.message ?: ""))
            }
        }
    }

    fun checkUserStatus(firebaseUid: String) {
        viewModelScope.launch {
            try {
                val response = api.getUserStatus(firebaseUid)
                _userStatus.value = response
                _authState.value = when (response.status) {
                    "APPROVED" -> AuthState.Authenticated(response.nombre, response.role)
                    "REJECTED" -> AuthState.Rejected
                    else -> AuthState.Pending
                }
            } catch (e: Exception) {
                // User not registered in backend yet — register now
                val user = auth.currentUser
                if (user != null) {
                    try {
                        val reg = api.registerUser(
                            RegisterRequest(
                                firebaseUid = user.uid,
                                email = user.email ?: "",
                                nombre = user.displayName ?: user.email ?: "Usuario"
                            )
                        )
                        _userStatus.value = reg
                        _authState.value = AuthState.Pending
                    } catch (regEx: Exception) {
                        Log.e("AuthViewModel", "Register error", regEx)
                        _authState.value = AuthState.Error("Error de conexión con el servidor")
                    }
                } else {
                    _authState.value = AuthState.NotAuthenticated
                }
            }
        }
    }

    fun loadPendingUsers() {
        viewModelScope.launch {
            try {
                val uid = auth.currentUser?.uid ?: return@launch
                _pendingUsers.value = api.getPendingUsers(uid)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error loading pending users", e)
            }
        }
    }

    fun approveUser(id: Long) {
        viewModelScope.launch {
            try {
                val uid = auth.currentUser?.uid ?: return@launch
                api.approveUser(id, uid)
                loadPendingUsers()
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error approving user", e)
            }
        }
    }

    fun rejectUser(id: Long) {
        viewModelScope.launch {
            try {
                val uid = auth.currentUser?.uid ?: return@launch
                api.rejectUser(id, uid)
                loadPendingUsers()
                loadAllUsers()
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error rejecting user", e)
            }
        }
    }

    fun loadAllUsers() {
        viewModelScope.launch {
            try {
                val uid = auth.currentUser?.uid ?: return@launch
                _allUsers.value = api.getAllUsers(uid)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error loading all users", e)
            }
        }
    }

    fun changeUserRole(id: Long, newRole: String) {
        viewModelScope.launch {
            try {
                val uid = auth.currentUser?.uid ?: return@launch
                api.changeUserRole(id, newRole, uid)
                loadAllUsers()
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error changing role", e)
            }
        }
    }

    fun deleteUser(id: Long) {
        viewModelScope.launch {
            try {
                val uid = auth.currentUser?.uid ?: return@launch
                api.deleteUser(id, uid)
                loadAllUsers()
                loadPendingUsers()
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error deleting user", e)
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _userStatus.value = null
        _authState.value = AuthState.NotAuthenticated
    }

    fun refreshStatus() {
        val uid = auth.currentUser?.uid ?: return
        checkUserStatus(uid)
    }

    private fun parseFirebaseError(error: String): String {
        return when {
            error.contains("INVALID_EMAIL", true) -> "Correo electrónico inválido"
            error.contains("USER_NOT_FOUND", true) -> "Usuario no encontrado"
            error.contains("WRONG_PASSWORD", true) -> "Contraseña incorrecta"
            error.contains("EMAIL_ALREADY_IN_USE", true) -> "Este correo ya está registrado"
            error.contains("WEAK_PASSWORD", true) -> "La contraseña debe tener al menos 6 caracteres"
            error.contains("NETWORK", true) -> "Error de conexión"
            error.contains("TOO_MANY", true) -> "Demasiados intentos. Intenta más tarde"
            error.contains("INVALID_LOGIN_CREDENTIALS", true) -> "Credenciales inválidas"
            else -> "Error: $error"
        }
    }
}

sealed class AuthState {
    object Loading : AuthState()
    object NotAuthenticated : AuthState()
    data class Authenticated(val nombre: String, val role: String) : AuthState()
    object Pending : AuthState()
    object Rejected : AuthState()
    data class Error(val message: String) : AuthState()
}
