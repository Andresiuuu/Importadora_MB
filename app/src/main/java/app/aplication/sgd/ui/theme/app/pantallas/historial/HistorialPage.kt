package app.aplication.sgd.ui.theme.app.pantallas.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.text.SimpleDateFormat
import java.util.Locale


@Preview
@Composable
fun HistorialPage (
    viewModel: ClientViewModel = viewModel() //Inyección de dependencias
){
    val listaClientes by viewModel.clientes.collectAsStateWithLifecycle()
    var isDescending by remember { mutableStateOf(true) }

    // Ordenar la lista por fecha
    val listaOrdenada = remember(listaClientes, isDescending) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        listaClientes.sortedWith { a, b ->
            try {
                val dateA = dateFormat.parse(a.registrationDate)
                val dateB = dateFormat.parse(b.registrationDate)
                if (isDescending) {
                    (dateB?.time ?: 0).compareTo(dateA?.time ?: 0)
                } else {
                    (dateA?.time ?: 0).compareTo(dateB?.time ?: 0)
                }
            } catch (e: Exception) {
                0
            }
        }
    }

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
                FilterButton(
                    isDescending = isDescending,
                    onClick = { isDescending = !isDescending }
                )
            }
            Space()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp,52.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            )
            {
                items(listaOrdenada.take(10).size) { index ->
                    CardClientesHistorial(
                        nombre = listaOrdenada[index].fullname,
                        ciudad = listaOrdenada[index].city,
                        fechaDeuda = listaOrdenada[index].registrationDate,
                        monto = listaOrdenada[index].debt.toString()
                    )
                }

            }
        }
    }
}
