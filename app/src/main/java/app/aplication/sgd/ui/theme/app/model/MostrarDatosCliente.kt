package app.aplication.sgd.ui.theme.app.model

import androidx.compose.runtime.Composable
import app.aplication.sgd.ui.theme.app.componentes.infoClientCard.InfoClientCard

@Composable
fun MostrarDatosCliente (){
    val cliente = Client()
    val nombreCliente = cliente.nombre
    val ciudadCliente = cliente.direccion
    val estadoUltActualizacion = cliente.estadoDeUltimaActualizacion
    val montoDeuda = cliente.montoDeuda
    val fechaModificacion = cliente.fechaModificacion

        InfoClientCard(
            nombreCliente,
            ciudadCliente,
            estadoUltActualizacion,
            montoDeuda,
            fechaModificacion
        )
    }
