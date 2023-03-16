package com.dalvarad.dogedex.doglist

import com.dalvarad.dogedex.R
import com.dalvarad.dogedex.models.Dog
import com.dalvarad.dogedex.api.ApiResponseStatus
import com.dalvarad.dogedex.api.DogsApi.retrofitService
import com.dalvarad.dogedex.api.dto.AddDogToUsetDTO
import com.dalvarad.dogedex.api.dto.DogDTOMaper
import com.dalvarad.dogedex.api.makeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {

        return withContext(Dispatchers.IO) {
            val allDogsListDeferred = async{ downloadDogs()}
            val userDogsListdeferred = async {  getUserdogs() }

            //este proceso espera a que todos los async terminen para optimizar el tiempo de carga
            val allDogsListResponse  = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListdeferred.await()

            if (allDogsListResponse is ApiResponseStatus.Error) {
                allDogsListResponse
            } else if (userDogsListResponse is ApiResponseStatus.Error) {
                userDogsListResponse
            } else if (allDogsListResponse is ApiResponseStatus.Succes && userDogsListResponse is ApiResponseStatus.Succes) {
                ApiResponseStatus.Succes(
                    getCollectionList(
                        allDogsListResponse.data,
                        userDogsListResponse.data
                    )
                )
            } else {
                ApiResponseStatus.Error(R.string.unknow_exception)
            }

        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>) = allDogList.map {
        if (userDogList.contains(it)) {
            it
        } else {
            Dog(
                0, it.index, "", "", "", "", "",
                "", "", "", "", false
            )
        }
    }.sorted()


    //los metodos suspend funcionan dentro de corrutinas
    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMaper()
        dogDTOMapper.fromDogDTOListToDOmainList(dogDTOList)
    }

    private suspend fun getUserdogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getUserDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMaper()
        dogDTOMapper.fromDogDTOListToDOmainList(dogDTOList)
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUsetDTO = AddDogToUsetDTO(dogId)
        val defaultResponse = retrofitService.addDogToUser(addDogToUsetDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

}