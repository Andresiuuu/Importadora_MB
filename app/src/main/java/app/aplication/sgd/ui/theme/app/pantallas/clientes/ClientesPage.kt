package app.aplication.sgd.ui.theme.app.pantallas.clientes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.aplication.sgd.ui.theme.app.componentes.SearchBar
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.app.componentes.clientForm.ClientFormCard
import app.aplication.sgd.ui.theme.app.componentes.infoClientCard.InfoClientCard
import app.aplication.sgd.ui.theme.app.componentes.infoClientCard.InfoClientCardEmpty
import app.aplication.sgd.ui.theme.app.viewModel.ClientViewModel
import app.aplication.sgd.ui.theme.background.LowPolyBackground
@Preview
@Composable
fun ClientesPage (
    viewModel: ClientViewModel = viewModel()
){
    val clientesFiltrados by viewModel.clientesFiltrados.collectAsStateWithLifecycle()
    var busqueda by rememberSaveable { mutableStateOf("") }
    var mostrarFormulario by rememberSaveable { mutableStateOf(false) }

    //Fondo low-poly
    LowPolyBackground()
    // Contenedor principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp, 54.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextUi("Clientes", 36, bold = true)
            Space()
            // Solo mostrar SearchBar si NO estamos en modo formulario
            if (!mostrarFormulario) {
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    SearchBar(
                        value = busqueda
                    ) { nuevaBusqueda ->
                        busqueda = nuevaBusqueda

                        if (nuevaBusqueda.isEmpty()) {
                            viewModel.limpiarBusqueda()
                        } else {
                            viewModel.buscarPorNombre(nuevaBusqueda)
                        }
                    }
                }
            }
        }
        Space()
        
        if (mostrarFormulario) {
            // Mostrar formulario para agregar cliente
            Space(13)
            ClientFormCard(
                onGuardar = { nombre, apellido, ubicacion, montoDeuda ->
                    Log.d("ClientesPage", "onGuardar llamado: $nombre, $apellido, $ubicacion, $montoDeuda")
                    val deuda = montoDeuda.toDoubleOrNull() ?: 0.0
                    viewModel.crearCliente(
                        nombre = nombre,
                        apellido = apellido,
                        ciudad = ubicacion,
                        deuda = deuda,
                        onSuccess = {
                            mostrarFormulario = false
                            busqueda = ""
                            viewModel.limpiarBusqueda()
                        }
                    )
                }
            )
        } else if (clientesFiltrados.isEmpty() && busqueda.isNotEmpty()) {
            // No se encontraron resultados
            Space(13)
            InfoClientCardEmpty(
                onAgregarCliente = { mostrarFormulario = true }
            )
        } else if (clientesFiltrados.isEmpty()) {
            // Campo de búsqueda vacío
            Space(13)
            InfoClientCardEmpty(
                onAgregarCliente = { mostrarFormulario = true }
            )
        } else {
            // Mostrar lista de clientes filtrados
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(clientesFiltrados) { cliente ->
                    // Formatear fecha a AAAA-MM-DD (tomar solo los primeros 10 caracteres)
                    val fechaFormateada = cliente.registrationDate.take(10)
                    InfoClientCard(
                        nombre = cliente.fullname,
                        ciudad = cliente.city,
                        fechaDeuda = fechaFormateada,
                        monto = "$${cliente.debt}"
                    )
                }
            }
        }
    }
}
