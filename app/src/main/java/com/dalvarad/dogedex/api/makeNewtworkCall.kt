package com.dalvarad.dogedex.api

import com.dalvarad.dogedex.R
import com.dalvarad.dogedex.api.dto.DogDTOMaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> = withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Succes(call())
        } catch (e: UnknownHostException){
            ApiResponseStatus.Error(R.string.unknow_host_exception)
        }catch (e: Exception){
            ApiResponseStatus.Error(R.string.unkonow_error)
        }
    }
