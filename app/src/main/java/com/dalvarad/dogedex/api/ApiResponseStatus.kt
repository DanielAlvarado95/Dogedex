package com.dalvarad.dogedex.api

sealed class ApiResponseStatus<T>() {

    class Succes<T>(val data: T): ApiResponseStatus<T>()
    class Loading<T>(): ApiResponseStatus<T>()
    class Error<T>(val message: Int): ApiResponseStatus<T>()
}