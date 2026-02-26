package app.aplication.sgd.ui.theme.app.model

import com.google.gson.annotations.SerializedName

data class UserStatusResponse(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("firebaseUid") val firebaseUid: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("status") val status: String = "PENDING",
    @SerializedName("role") val role: String = "USER",
    @SerializedName("createdAt") val createdAt: String? = null
)

data class RegisterRequest(
    @SerializedName("firebaseUid") val firebaseUid: String,
    @SerializedName("email") val email: String,
    @SerializedName("nombre") val nombre: String
)
