package com.intelliswift.mvvmsetupapps.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data") var data: Data? = Data(),
    @SerializedName("result") var result: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("error_code") var errorCode: Int? = null
)