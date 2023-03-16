package com.dalvarad.dogedex.api.dto

import com.squareup.moshi.Json

class USerDTO(
    val id: Long,
    val email: String,
    @field:Json(name = "authentication_token") val authenticationToken: String,
)