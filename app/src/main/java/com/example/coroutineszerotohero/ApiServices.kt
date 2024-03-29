package com.example.coroutineszerotohero

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("/api/10159690569607252/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroName:String): Response<SuperHeroDataResponse>
}

data class SuperHeroDataResponse(@SerializedName("response") val response: String)
