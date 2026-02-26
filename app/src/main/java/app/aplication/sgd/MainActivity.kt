package app.aplication.sgd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.aplication.sgd.ui.theme.app.pantallas.login.LoginScreen
import app.aplication.sgd.ui.theme.app.pantallas.login.PendingApprovalScreen
import app.aplication.sgd.ui.theme.app.pantallas.login.RegisterScreen
import app.aplication.sgd.ui.theme.app.pantallas.login.RejectedScreen
import app.aplication.sgd.ui.theme.app.pantallas.main.MainScreen
import app.aplication.sgd.ui.theme.app.viewModel.AuthState
import app.aplication.sgd.ui.theme.app.viewModel.AuthViewModel
import app.aplication.sgd.ui.theme.background.LowPolyBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppRoot()
        }
    }
}

@Composable
fun AppRoot(authViewModel: AuthViewModel = viewModel()) {
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    var showRegister by rememberSaveable { mutableStateOf(false) }

    when (val state = authState) {
        is AuthState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                LowPolyBackground()
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is AuthState.NotAuthenticated, is AuthState.Error -> {
            val errorMsg = (state as? AuthState.Error)?.message
            val isLoading = false

            if (showRegister) {
                RegisterScreen(
                    onSignUp = { email, password, nombre ->
                        authViewModel.signUp(email, password, nombre)
                    },
                    onNavigateToLogin = { showRegister = false },
                    isLoading = isLoading,
                    errorMessage = errorMsg
                )
            } else {
                LoginScreen(
                    onSignIn = { email, password ->
                        authViewModel.signIn(email, password)
                    },
                    onNavigateToRegister = { showRegister = true },
                    isLoading = isLoading,
                    errorMessage = errorMsg
                )
            }
        }

        is AuthState.Pending -> {
            PendingApprovalScreen(
                onRefresh = { authViewModel.refreshStatus() },
                onSignOut = { authViewModel.signOut() }
            )
        }

        is AuthState.Rejected -> {
            RejectedScreen(
                onSignOut = { authViewModel.signOut() }
            )
        }

        is AuthState.Authenticated -> {
            MainScreen(
                userName = state.nombre,
                isAdmin = state.role == "ADMIN",
                authViewModel = authViewModel,
                onSignOut = { authViewModel.signOut() }
            )
        }
    }
}

