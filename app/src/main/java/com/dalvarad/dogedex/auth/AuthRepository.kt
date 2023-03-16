package com.dalvarad.dogedex.auth

import com.dalvarad.dogedex.models.User
import com.dalvarad.dogedex.api.ApiResponseStatus
import com.dalvarad.dogedex.api.DogsApi
import com.dalvarad.dogedex.api.dto.LoginDTO
import com.dalvarad.dogedex.api.dto.SignUpDTO
import com.dalvarad.dogedex.api.dto.UserDtoMapper
import com.dalvarad.dogedex.api.makeNetworkCall

class AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val loginDTO = LoginDTO(email,password)
        val loginResponse = DogsApi.retrofitService.login(loginDTO)

        //controla si el signup es correcto si no tira error
        if (!loginResponse.isSucces){
            throw Exception(loginResponse.message)
        }

        val userDTO = loginResponse.data.user
        val userDTOMaper = UserDtoMapper()
        userDTOMaper.fromUserDTOToUserDomain(userDTO)
    }

    //los metodos suspend funcionan dentro de corrutinas
    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email,password, passwordConfirmation)
        val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)

        //controla si el signup es correcto si no tira error
        if (!signUpResponse.isSucces){
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMaper = UserDtoMapper()
        userDTOMaper.fromUserDTOToUserDomain(userDTO)
    }

}