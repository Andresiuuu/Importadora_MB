package app.aplication.sgd.ui.theme.app.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class Client(
    @SerialName("city")
    val city: String = "Sin datos",
    @SerialName("debt")
    val debt: Double = 0.0,
    @SerialName("discount")
    val discount: Boolean = false,
    @SerializedName("fullName")
    val fullname: String ="Sin datos",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("payment")
    val payment: Double? = 0.0,
    @SerialName("registrationDate")
    val registrationDate: String = "Sin datos",
    @SerialName("status")
    val status: String? = "ACTIVE",
    @SerialName("totalAmount")
    val totalAmount: Double? = 0.0
)
