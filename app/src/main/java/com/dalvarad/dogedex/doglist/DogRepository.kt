package com.dalvarad.dogedex.doglist

import com.dalvarad.dogedex.Dog
import com.dalvarad.dogedex.api.ApiResponseStatus
import com.dalvarad.dogedex.api.DogsApi.retrofitService
import com.dalvarad.dogedex.api.dto.DogDTOMaper
import com.dalvarad.dogedex.api.makeNetworkCall

class DogRepository {

    //los metodos suspend funcionan dentro de corrutinas
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMaper()
            dogDTOMapper.fromDogDTOListToDOmainList(dogDTOList)
        }
}