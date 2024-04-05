package com.intelliswift.mvvmsetupapps.models

data class UserRequest(
    val driver_first_name: String,
    val is_carpool: Boolean,
    val mobile_device_id: String,
    val mobile_device_type: String,
    val mobile_number: String,
    var password: String
)