package app.aplication.sgd.ui.theme.app.viewModel

import androidx.lifecycle.ViewModel
import app.aplication.sgd.ui.theme.app.model.Client
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import app.aplication.sgd.ui.theme.app.model.RetrofitClient
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ClientViewModel: ViewModel() {
    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clientes: StateFlow<List<Client>> = _clients
    init {
        cargarClientes()
    }

    fun cargarClientes(){
        viewModelScope.launch {
            try{
                val response = RetrofitClient.api.getClients()
                if(response.isSuccessful){
                    _clients.value = response.body()?: emptyList()
                }
            }catch (e: Exception){
                // Manejar errores de la solicitud
            }
        }
    }
}