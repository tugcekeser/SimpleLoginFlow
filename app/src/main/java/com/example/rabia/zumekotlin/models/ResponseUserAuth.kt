package com.example.rabia.zumekotlin.models

import com.google.gson.annotations.SerializedName

data class ResponseUserAuth(
        @SerializedName("token") val userToken: String?
)