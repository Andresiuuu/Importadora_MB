package app.aplication.sgd.ui.theme.app.model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
}
