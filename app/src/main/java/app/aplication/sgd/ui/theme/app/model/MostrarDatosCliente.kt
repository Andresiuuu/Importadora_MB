package app.aplication.sgd.ui.theme.app.model

import androidx.compose.runtime.Composable
import app.aplication.sgd.ui.theme.app.componentes.infoClientCard.InfoClientCard

@Composable
fun MostrarDatosCliente (){
    val cliente = Client(

    )
    val nombreCliente = cliente.fullname
    val ciudadCliente = cliente.city
    val estadoUltActualizacion = cliente.registrationDate
    val montoDeuda = cliente.debt

        InfoClientCard(
            cliente.id,
            nombreCliente,
            ciudadCliente,
            estadoUltActualizacion,
            montoDeuda.toString()
        )
    }
