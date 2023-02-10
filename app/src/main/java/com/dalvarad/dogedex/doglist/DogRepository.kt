package com.dalvarad.dogedex.doglist

import com.dalvarad.dogedex.Dog
import com.dalvarad.dogedex.api.DogsApi.retrofitService
import com.dalvarad.dogedex.api.dto.DogDTOMaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {

    //los metodos suspend funcionan dentro de corrutinas
    suspend fun downloadDogs(): List<Dog> {
        return withContext(Dispatchers.IO) {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMaper()
            dogDTOMapper.fromDogDTOListToDOmainList(dogDTOList)
        }
    }
}