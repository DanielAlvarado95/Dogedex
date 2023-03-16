package com.dalvarad.dogedex.api

import com.dalvarad.dogedex.*
import com.dalvarad.dogedex.api.dto.AddDogToUsetDTO
import com.dalvarad.dogedex.api.dto.LoginDTO
import com.dalvarad.dogedex.api.dto.SignUpDTO
import com.dalvarad.dogedex.api.responses.DogListApiResponse
import com.dalvarad.dogedex.api.responses.AuthApiResponse
import com.dalvarad.dogedex.api.responses.DefaultResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface APiService {
    @GET(GET_ALL_DOGS)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER)
    suspend fun addDogToUser(@Body addDogToUsetDTO: AddDogToUsetDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun  getUserDogs(): DogListApiResponse

}


object DogsApi {
    val retrofitService: APiService by lazy {
        retrofit.create(APiService::class.java)
    }
}