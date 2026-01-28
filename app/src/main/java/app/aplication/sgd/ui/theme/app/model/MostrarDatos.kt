package app.aplication.sgd.ui.theme.app.model

import androidx.compose.runtime.Composable
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.card.CardClientesHistorial

@Composable
fun MostrarDatosHistorial (){
    val nombreCliente = Cliente.nombre
    val ciudadCliente = Cliente.direccion
    val estadoUltActualizacion = Cliente.estadoDeUltimaActualizacion
    val montoDeuda = Cliente.montoDeuda

    for (i in 1..10){
        CardClientesHistorial(
            nombreCliente,
            ciudadCliente,
            estadoUltActualizacion,
            montoDeuda
        )
        Space(30)
    }
}