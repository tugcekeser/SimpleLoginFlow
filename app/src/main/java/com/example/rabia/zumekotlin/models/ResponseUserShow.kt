package com.example.rabia.zumekotlin.models

import com.google.gson.annotations.SerializedName

data class ResponseUserShow(
        @SerializedName("user") val user: List<User>?
)
