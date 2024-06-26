package com.intelliswift.mvvmsetupapps.ui.login

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intelliswift.mvvmsetupapps.models.UserRequest
import com.intelliswift.mvvmsetupapps.models.UserResponse
import com.intelliswift.mvvmsetupapps.repository.UserRepository
import com.intelliswift.mvvmsetupapps.utils.Helper
import com.intelliswift.mvvmsetupapps.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
    get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredentials(emailAddress: String, userName: String, password: String,
     isLogin: Boolean) : Pair<Boolean, String> {

        var result = Pair(true, "")
        if(TextUtils.isEmpty(emailAddress) || (!isLogin && TextUtils.isEmpty(userName)) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide the credentials")
        }
        else if(!Helper.isValidPhoneNumber(emailAddress)){
            result = Pair(false, "Invalid phone number")
        }
        else if(!TextUtils.isEmpty(password) && password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }
}