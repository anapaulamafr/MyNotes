package com.example.mynotes

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class Retrofit {

    fun init() {
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun apiRetrofitService(): APIRetrofitService {
        return retrofit.create(APIRetrofitService::class.java)
    }
}

interface APIRetrofitService {

    @GET("{CEP}/json/")
    fun getEnderecoByCEP(@Path("CEP") CEP : String) : Call<CEP>

    @GET("{estado}/{cidade}/{endereco}/json/")
    fun getCEPByCidadeEstadoEndereco(@Path("estado") estado: String,
                                     @Path("cidade") cidade: String,
                                     @Path("endereco") endereco: String): Call<List<CEP>>

}