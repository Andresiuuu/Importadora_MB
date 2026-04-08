package app.aplication.sgd.ui.theme.app.viewModel

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.aplication.sgd.ui.theme.app.model.Client
import app.aplication.sgd.ui.theme.app.model.ClientRequest
import app.aplication.sgd.ui.theme.app.model.PaymentRequest
import app.aplication.sgd.ui.theme.app.model.RegistroAbono
import app.aplication.sgd.ui.theme.app.model.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ClientViewModel: ViewModel() {
    // 1. Creamos el estado privado que mutará
    private val _totalDeudaGlobal = MutableStateFlow(0.0)
    private val _clientes = MutableStateFlow<List<Client>>(emptyList())
    private val _cliente = MutableStateFlow<Client?>(null)
    private val _clientesFiltrados = MutableStateFlow<List<Client>>(emptyList())
    private val _abonos = MutableStateFlow<List<RegistroAbono>>(emptyList())
    private val _isRefreshing = MutableStateFlow(false)
    private val _actualizacionesRecientes = MutableStateFlow("0")
    val actualizacionesRecientes: StateFlow<String> = _actualizacionesRecientes
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    // 2. Estado público que la vista observará (solo lectura)
    val clientes: StateFlow<List<Client>> = _clientes
    val cliente: StateFlow<Client?> = _cliente
    val clientesFiltrados: StateFlow<List<Client>> = _clientesFiltrados
    val abonos: StateFlow<List<RegistroAbono>> = _abonos
    val totalDeudaGlobal: StateFlow<Double> = _totalDeudaGlobal

    init {
        refreshAll()
        buscarCliente()
    }
    fun limpiarBusqueda() {
        _cliente.value = null
        _clientesFiltrados.value = _clientes.value // Cambiado de emptyList() a la lista completa
    }

    fun buscarPorNombre(nombre: String) {
        if (nombre.isBlank()) {
            // Si borra la búsqueda, mostramos toda la lista (que ya está filtrada y ordenada)
            _clientesFiltrados.value = _clientes.value
            return
        }

        _clientesFiltrados.value = _clientes.value.filter {
            it.fullname.contains(nombre, ignoreCase = true)
        }
        // No hace falta re-ordenar aquí porque _clientes.value ya viene ordenada de refreshAll
    }

    fun refreshAll() {
        viewModelScope.launch {
            if (_isRefreshing.value) return@launch
            _isRefreshing.value = true

            try {
                val service = RetrofitClient.makeRetrofitService()
                val clientesResponse = service.getClients()


                // APLICAMOS FILTRO Y ORDEN:
                val clientesProcesados = clientesResponse
                    .filter { it.status != "CANCELLED" } // 1. Quitar cancelados
                    .sortedByDescending { it.registrationDate } // 2. Más recientes primero

                _clientes.value = clientesProcesados

                // Si la búsqueda está vacía, actualizamos también los filtrados para que se vea la lista completa
                _clientesFiltrados.value = clientesProcesados

                val suma = clientesProcesados.sumOf { it.debt ?: it.totalAmount ?: 0.0 }
                _totalDeudaGlobal.value = suma

                val haceCincoDias = java.time.LocalDateTime.now().minusDays(5)
                val actualizacionesRecientes = clientesProcesados.count { cliente ->
                    try {
                        val fechaCliente = java.time.LocalDateTime.parse(cliente.registrationDate)
                        fechaCliente.isAfter(haceCincoDias)
                    } catch (e: Exception) {
                        false
                    }
                }
                _actualizacionesRecientes.value = actualizacionesRecientes.toString()

            } catch (e: Exception) {
                Log.e("ClientViewModel", "Error refrescando: ${e.message}")
            } finally {
                _isRefreshing.value = false
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
                refreshAll()
                onSuccess()
            } catch (e: Exception) {
                Log.e("ClientViewModel", "Error creando cliente: ${e.message}", e)
                onError(e.message ?: "Error desconocido")
            }
        }
    }

    fun registrarAbono(
        clientId: Int,
        monto: Double,
        clientName: String = "",
        clientCity: String = "",
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val request = PaymentRequest(amount = monto)
                RetrofitClient.makeRetrofitService().registerPayment(clientId, request)
                Log.d("ClientViewModel", "Abono registrado: $$monto para cliente $clientId")
                // Guardar registro de abono en historial local
                val fechaActual = java.time.LocalDate.now().toString()
                val registro = RegistroAbono(
                    clientName = clientName,
                    city = clientCity,
                    paymentDate = fechaActual,
                    amount = monto
                )
                _abonos.value = listOf(registro) + _abonos.value
                refreshAll()
                onSuccess()
            } catch (e: Exception) {
                Log.e("ClientViewModel", "Error registrando abono: ${e.message}", e)
                onError(e.message ?: "Error desconocido")
            }
        }
    }
}
