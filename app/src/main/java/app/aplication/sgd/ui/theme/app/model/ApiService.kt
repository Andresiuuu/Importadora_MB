package app.aplication.sgd.ui.theme.app.model

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("clients")
    suspend fun getClients(): List<Client>

    @GET("clients/{id}")
    suspend fun getClientById(
        @Path("id") id: Int
    ): Client

    @POST("clients")
    suspend fun createClient(
        @Body client: ClientRequest
    ): Client

    @POST("clients/{id}/payments") //Esto agrega abonos a un cliente y lo resta a su deuda
    suspend fun registerPayment(
        @Path("id") id: Int,
        @Body payment: PaymentRequest
    ): Client


    // Auth endpoints
    @POST("auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): UserStatusResponse

    @GET("auth/status/{firebaseUid}")
    suspend fun getUserStatus(
        @Path("firebaseUid") firebaseUid: String
    ): UserStatusResponse

    @GET("auth/pending")
    suspend fun getPendingUsers(
        @Header("X-Admin-Uid") adminUid: String
    ): List<UserStatusResponse>

    @PUT("auth/approve/{id}")
    suspend fun approveUser(
        @Path("id") id: Long,
        @Header("X-Admin-Uid") adminUid: String
    ): UserStatusResponse

    @PUT("auth/reject/{id}")
    suspend fun rejectUser(
        @Path("id") id: Long,
        @Header("X-Admin-Uid") adminUid: String
    ): UserStatusResponse

    @GET("auth/users")
    suspend fun getAllUsers(
        @Header("X-Admin-Uid") adminUid: String
    ): List<UserStatusResponse>

    @PUT("auth/users/{id}/role")
    suspend fun changeUserRole(
        @Path("id") id: Long,
        @Query("role") role: String,
        @Header("X-Admin-Uid") adminUid: String
    ): UserStatusResponse

    @DELETE("auth/users/{id}")
    suspend fun deleteUser(
        @Path("id") id: Long,
        @Header("X-Admin-Uid") adminUid: String
    )
}
