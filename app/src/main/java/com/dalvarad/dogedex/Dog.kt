package com.dalvarad.dogedex

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dog(
    //@field:Json(name = "name_es") sirve para cambial el nombre a como vinene en el servicio
    val id: Long,
    val index: Int,
    @field:Json(name = "name_es") val name: String,
    @field:Json(name = "dog_type") val type: String,
    @field:Json(name = "height_female") val heightFemaile: String,
    @field:Json(name = "height_male") val heightMale: String,
    @field:Json(name = "image_url") val imageUrl: String,
    @field:Json(name = "life_expectancy") val lifeExpectancy: String,
    val temperament: String,
    @field:Json(name = "weight_female") val weightFemale: String,
    @field:Json(name = "weight_male") val weightMale: String
) : Parcelable