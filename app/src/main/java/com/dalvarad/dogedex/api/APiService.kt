package com.dalvarad.dogedex.api

import com.dalvarad.dogedex.BASE_URL
import com.dalvarad.dogedex.Dog
import com.dalvarad.dogedex.GET_ALL_DOGS
import com.dalvarad.dogedex.api.responses.DogListApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface APiService{
    @GET(GET_ALL_DOGS)
    suspend fun getAllDogs(): DogListApiResponse
}

object DogsApi{
    val retrofitService : APiService by lazy {
        retrofit.create(APiService::class.java)
    }
}