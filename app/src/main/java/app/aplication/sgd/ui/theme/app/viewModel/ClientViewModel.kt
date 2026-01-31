package app.aplication.sgd.ui.theme.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.aplication.sgd.ui.theme.app.model.Client
import app.aplication.sgd.ui.theme.app.model.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ClientViewModel: ViewModel() {
    // 1. Creamos el estado privado que mutará
    private val _clientes = MutableStateFlow<List<Client>>(emptyList())

    // 2. Estado público que la vista observará (solo lectura)
    val clientes: StateFlow<List<Client>> = _clientes
    init {
        cargarClientes()
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
}