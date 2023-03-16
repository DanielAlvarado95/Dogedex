package com.dalvarad.dogedex.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dalvarad.dogedex.api.ApiResponseStatus
import com.dalvarad.dogedex.models.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _User = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _User

    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status: LiveData<ApiResponseStatus<User>>
        get() = _status

    private val authRepository = AuthRepository()


    fun login(email: String, password:String){
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.login(email, password))
        }

    }

    fun signUp(email: String, password: String, passwordConfirmation: String) {
        //Sirve para hacer el signun en corrutinas
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.signUp(email, password, passwordConfirmation))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if (apiResponseStatus is ApiResponseStatus.Succes) {
            _User.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus
    }

}