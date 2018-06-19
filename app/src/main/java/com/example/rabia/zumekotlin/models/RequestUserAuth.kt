package com.example.rabia.zumekotlin.models

import com.google.gson.annotations.SerializedName

data class RequestUserAuth(
        @SerializedName("phone") val userPhone: String?,
        @SerializedName("password") val userPassword: String?
)