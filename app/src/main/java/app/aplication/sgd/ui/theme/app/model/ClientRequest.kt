package app.aplication.sgd.ui.theme.app.model

import com.google.gson.annotations.SerializedName

data class ClientRequest(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("city")
    val city: String?=null,
    @SerializedName("registrationDate")
    val registrationDate: String? = null,
    @SerializedName("initialDebt")
    val initialDebt: Double? = null,
    @SerializedName("discount")
    val discount: Boolean? = null
)
