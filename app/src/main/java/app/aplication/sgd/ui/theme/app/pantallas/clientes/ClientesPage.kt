package app.aplication.sgd.ui.theme.app.pantallas.clientes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import app.aplication.sgd.ui.theme.app.componentes.agregarAbonoCard.AgregarAbonoCard
import app.aplication.sgd.ui.theme.app.componentes.clientForm.ClientFormCard
import app.aplication.sgd.ui.theme.app.componentes.infoClientCard.InfoClientCard
import app.aplication.sgd.ui.theme.app.componentes.infoClientCard.InfoClientCardEmpty
import app.aplication.sgd.ui.theme.app.model.Client
import app.aplication.sgd.ui.theme.app.viewModel.ClientViewModel
import app.aplication.sgd.ui.theme.background.LowPolyBackground
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientesPage (
    viewModel: ClientViewModel = viewModel()
){
    val clientesFiltrados by viewModel.clientesFiltrados.collectAsStateWithLifecycle()
    var busqueda by rememberSaveable { mutableStateOf("") }
    var mostrarFormulario by rememberSaveable { mutableStateOf(false) }
    var clienteAbono by rememberSaveable { mutableStateOf<Client?>(null) }
    var paginaActual by remember { mutableIntStateOf(0) }
    var montoAbono by rememberSaveable { mutableStateOf("") }
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

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
        Space(20)

        // 1. Prioridad: Mostrar el formulario de abono si hay un cliente seleccionado
        if (clienteAbono != null) {
            val cliente = clienteAbono!!
            val fechaFormateada = cliente.registrationDate.take(10)
            Space(13)
            AgregarAbonoCard(
                nombre = cliente.fullname,
                ciudad = cliente.city,
                fechaDeuda = fechaFormateada,
                monto = "${cliente.totalAmount}",
                abono = montoAbono,
                onvalueChange = { montoAbono = it },
                onAbonar = {
                    val monto = montoAbono.toDoubleOrNull() ?: 0.0
                    if (monto > 0) {
                        viewModel.registrarAbono(
                            clientId = cliente.id,
                            monto = monto,
                            clientName = cliente.fullname,
                            clientCity = cliente.city,
                            onSuccess = {
                                busqueda = ""
                                viewModel.limpiarBusqueda()
                                clienteAbono = null // Importante cerrar después de éxito
                            }
                        )
                        montoAbono = ""
                    }
                },
                onCancelar = {
                    montoAbono = ""
                    clienteAbono = null
                }
            )
        }
        // 2. Mostrar formulario de creación
        else if (mostrarFormulario) {
            Space(13)
            ClientFormCard(
                onCancelar = { mostrarFormulario = false },
                onGuardar = { nombre, apellido, ubicacion, montoDeuda ->
                    val deuda = montoDeuda.toDoubleOrNull() ?: 0.0
                    viewModel.crearCliente(
                        nombre = nombre,
                        apellido = apellido,
                        ciudad = ubicacion,
                        deuda = deuda,
                        onSuccess = {
                            busqueda = ""
                            viewModel.limpiarBusqueda()
                            mostrarFormulario = false
                        }
                    )
                }
            )
        }
        // 3. CAMBIO CLAVE: Si la búsqueda está VACÍA, forzamos InfoClientCardEmpty
        else if (busqueda.isBlank()) {
            Space(13)
            InfoClientCardEmpty(
                onAgregarCliente = { mostrarFormulario = true }
            )
        }
        // 4. Si hay texto pero la lista está vacía (No se encontró nada)
        else if (clientesFiltrados.isEmpty()) {
            Space(13)
            // Aquí podrías poner un texto de "No se encontraron resultados"
            // o seguir usando la card vacía:
            InfoClientCardEmpty(
                onAgregarCliente = { mostrarFormulario = true }
            )
        }
        // 5. Si hay texto y SÍ hay resultados
        else {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refreshAll() },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(clientesFiltrados) { cliente ->
                        val fechaFormateada = cliente.registrationDate.take(10)
                        val montoAMostrar = if (cliente.totalAmount != null && cliente.totalAmount != 0.0) {
                            "${cliente.totalAmount}"
                        } else {
                            "${cliente.debt}"
                        }

                        InfoClientCard(
                            nombre = cliente.fullname,
                            ciudad = cliente.city,
                            fechaDeuda = fechaFormateada,
                            monto = montoAMostrar,
                            onAñadirAbono = { clienteAbono = cliente }
                        )
                    }
                }
            }
        }
    }
}
