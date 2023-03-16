package com.dalvarad.dogedex.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dog(
    //@field:Json(name = "name_es") sirve para cambial el nombre a como vinene en el servicio
    val id: Long,
    val index: Int,
    val name: String,
    val type: String,
     val heightFemaile: String,
    val heightMale: String,
    val imageUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val weightFemale: String,
    val weightMale: String,
    val inCollection:Boolean = true
) : Parcelable, Comparable<Dog> {
    //sirve para ordenar los perros por indice
    override fun compareTo(other: Dog) =
        if (this.index > other.index) {
            1
        } else {
            -1
        }


}