package com.dalvarad.dogedex.api.responses

import com.squareup.moshi.Json

class AuthApiResponse (
    val message: String,
    @field:Json(name = "is_success") val isSucces: Boolean,
    val data: UserResponse,
        )