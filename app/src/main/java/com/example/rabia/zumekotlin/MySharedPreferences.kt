package com.example.rabia.zumekotlin

import android.content.Context
import com.example.rabia.zumekotlin.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MySharedPreferences(context: Context) {

    val PREFERENCE_NAME = "MySharedPreferences"
    val PREFERENCE_USER_TOKEN = "PrefUserToken"
    val PREFERENCE_USER = "PrefUser"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getUserToken(): String {
        return preference.getString(PREFERENCE_USER_TOKEN, "")
    }

    fun setUserToken(token: String?) {
        val editor = preference.edit()
        editor.putString(PREFERENCE_USER_TOKEN, token)
        editor.apply()
    }

    fun logout() {
        val editor = preference.edit()
        editor.remove(PREFERENCE_USER_TOKEN).commit();
        editor.remove(PREFERENCE_USER).commit();

    }

    fun getUser(): User {
        val gson = Gson();
        val response = preference.getString(PREFERENCE_USER, null);
        val gsonBuilder = Gson()

        val user: User = gsonBuilder.fromJson(response, object : TypeToken<User>() {}.type)
        return user
    }

    fun setUser(user: User?) {

        val gson = Gson();
        val json = gson.toJson(user);

        val editor = preference.edit()
        editor.putString(PREFERENCE_USER, json)
        editor.apply()
    }

}