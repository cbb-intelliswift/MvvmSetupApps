package com.intelliswift.mvvmsetupapps.models

import com.google.gson.annotations.SerializedName

data class Driver (

  @SerializedName("id") var id: Int? = null,
  @SerializedName("driver_first_name") var driverFirstName: String? = null,
  @SerializedName("mobile_number") var mobileNumber: String? = null,
  @SerializedName("auth_token") var authToken: String? = null
)