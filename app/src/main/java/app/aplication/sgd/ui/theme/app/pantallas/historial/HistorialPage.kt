package app.aplication.sgd.ui.theme.app.pantallas.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.aplication.sgd.ui.theme.app.componentes.FilterButton
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.app.viewModel.ClientViewModel
import app.aplication.sgd.ui.theme.background.LowPolyBackground
import app.aplication.sgd.ui.theme.app.componentes.card.CardClientesHistorial
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun HistorialPage (
    viewModel: ClientViewModel = viewModel() //Inyección de dependencias
){
    val listaClientes by viewModel.clientes.collectAsStateWithLifecycle()

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
            TextUi("Historial", 36, bold = true)
            Space()
            Row (
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                FilterButton()
            }
            Space()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp,52.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            )
            {
                items(listaClientes.take(10).size) { cliente ->
                    CardClientesHistorial(
                        nombre = listaClientes[cliente].fullname,
                        ciudad = listaClientes[cliente].city,
                        fechaDeuda = listaClientes[cliente].registrationDate,
                        monto = listaClientes[cliente].debt.toString()
                    )
                }

            }
        }
    }
}
