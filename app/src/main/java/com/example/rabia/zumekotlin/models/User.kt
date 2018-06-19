package com.example.rabia.zumekotlin.models

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") val id: Integer?,
        @SerializedName("phone") val phone: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?,
        @SerializedName("first_name") val firstName: String?,
        @SerializedName("last_name") val lastName: String?
)


