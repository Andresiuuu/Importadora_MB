package app.aplication.sgd.ui.theme.app.model

data class Client(
    val id: Int = 0,
    val nombre: String = "Sin datos",
    val direccion: String = "Sin datos",
    val estadoDeUltimaActualizacion:String = "Sin datos",
    val montoDeuda:String = "0",
    val fechaModificacion:String = "Sin datos"
)
