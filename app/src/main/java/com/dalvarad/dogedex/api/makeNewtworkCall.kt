package com.dalvarad.dogedex.api

import com.dalvarad.dogedex.R
import com.dalvarad.dogedex.api.dto.DogDTOMaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

private const val UNAUTHORIZED_ERROR_CODE = 401
suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> = withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Succes(call())
        }
        //valida si hay internet
        catch (e: UnknownHostException){
            ApiResponseStatus.Error(R.string.unknow_host_exception)

        }
        //Valida si ususario y contraseña son correctos con el code 401
        catch (e: HttpException){
            val errorMessage = if (e.code() == UNAUTHORIZED_ERROR_CODE){
                R.string.wrong_user_or_password
            }else{
                R.string.unkonow_error
            }
            ApiResponseStatus.Error(errorMessage)
        }
        //Maneja los errores dependiendo de la respuesta
        catch (e: Exception){
          val errorMessage =  when(e.message){
                "sign_up_error" -> R.string.error_sign_up
                "sign_in_error" -> R.string.error_sign_in
                "user_already_exists" -> R.string.user_already_exists
              "error_adding_dog" -> R.string.error_adding_dog
                else -> R.string.unkonow_error
            }
            ApiResponseStatus.Error(errorMessage)
        }
    }
