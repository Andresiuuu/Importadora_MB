package app.aplication.sgd.ui.theme.app.pantallas.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.aplication.sgd.ui.theme.app.componentes.FilterButton
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.app.model.MostrarDatosHistorial
import app.aplication.sgd.ui.theme.background.LowPolyBackground
@Preview
@Composable
fun HistorialPage (){

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
            {
                MostrarDatosHistorial()

                Space(13)
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                }

            }
        }
    }
}
