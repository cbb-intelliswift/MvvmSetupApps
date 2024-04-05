package com.intelliswift.mvvmsetupapps.models

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("driver") var driver: Driver? = Driver()
)