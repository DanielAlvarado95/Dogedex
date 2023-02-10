package com.dalvarad.dogedex.api.responses

import com.squareup.moshi.Json

class DogListApiResponse(
    val message: String,
    @field:Json(name = "is_succes") val isSucces: Boolean,
    val data: DogListResponse
)