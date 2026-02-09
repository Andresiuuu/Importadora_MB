package app.aplication.sgd.ui.theme.app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.aplication.sgd.ui.theme.app.model.Client
import app.aplication.sgd.ui.theme.app.model.ClientRequest
import app.aplication.sgd.ui.theme.app.model.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ClientViewModel: ViewModel() {
    // 1. Creamos el estado privado que mutará
    private val _clientes = MutableStateFlow<List<Client>>(emptyList())
    private val _cliente = MutableStateFlow<Client?>(null)
    private val _clientesFiltrados = MutableStateFlow<List<Client>>(emptyList())

    // 2. Estado público que la vista observará (solo lectura)
    val clientes: StateFlow<List<Client>> = _clientes
    val cliente: StateFlow<Client?> = _cliente
    val clientesFiltrados: StateFlow<List<Client>> = _clientesFiltrados


    init {
        cargarClientes()
        buscarCliente()
    }
    fun limpiarBusqueda() {
        _cliente.value = null // Esto hará que 'obtenerCliente' en la UI sea null inmediatamente
        _clientesFiltrados.value = emptyList()
    }

    fun buscarPorNombre(nombre: String) {
        if (nombre.isBlank()) {
            _clientesFiltrados.value = emptyList()
            return
        }
        _clientesFiltrados.value = _clientes.value.filter {
            it.fullname.contains(nombre, ignoreCase = true)
        }
    }

    fun cargarClientes() {
        viewModelScope.launch {
            try{
                // 3. Guardamos la respuesta de la API en nuestro estado
                val response = RetrofitClient.makeRetrofitService().getClients()
                _clientes.value = response
            } catch (e: Exception) {
                println("Error cargando clientes: ${e.message}")
            }
        }
    }
fun buscarCliente(id: Int = 0){
        viewModelScope.launch {
            try{
                val resultado  = RetrofitClient.makeRetrofitService().getClientById(id)
                _cliente.value = resultado

            }catch (e: Exception){
                println("Error cargando clientes: ${e.message}")
            }
        }
    }

    fun crearCliente(
        nombre: String,
        apellido: String,
        ciudad: String,
        deuda: Double,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        Log.d("ClientViewModel", "crearCliente llamado: $nombre $apellido, $ciudad, $deuda")
        viewModelScope.launch {
            try {
                val fechaActual = java.time.LocalDateTime.now().toString()
                val fechaActual = java.time.LocalDate.now().toString()
                val clientRequest = ClientRequest(
                    firstName = nombre,
                    lastName = apellido,
                    city = ciudad,
                    registrationDate = fechaActual,
                    initialDebt = deuda,
                    discount = false
                )
                Log.d("ClientViewModel", "Enviando request: $clientRequest")
                val response = RetrofitClient.makeRetrofitService().createClient(clientRequest)
                Log.d("ClientViewModel", "Cliente creado exitosamente: $response")
                // Recargar la lista de clientes después de crear uno nuevo
                cargarClientes()
                onSuccess()
            } catch (e: Exception) {
                Log.e("ClientViewModel", "Error creando cliente: ${e.message}", e)
                onError(e.message ?: "Error desconocido")
            }
        }
    }
}
