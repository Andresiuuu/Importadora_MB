package app.aplication.sgd.ui.theme.app.model

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("clients")
    suspend fun getClients(): Response<List<Client>>
}