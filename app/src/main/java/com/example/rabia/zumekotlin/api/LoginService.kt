package com.example.rabia.zumekotlin.api

import android.text.TextUtils
import com.example.rabia.zumekotlin.models.RequestUserAuth
import com.example.rabia.zumekotlin.models.ResponseUserAuth
import com.example.rabia.zumekotlin.models.ResponseUserShow
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

import okhttp3.logging.HttpLoggingInterceptor;


interface LoginService {

    //TODO: Use your own API routes
    @POST("user/auth")
    fun userAuth(@Body requestUserAuth: RequestUserAuth): Observable<ResponseUserAuth>

    @GET("user/show")
    fun showUser(): Observable<ResponseUserShow>


    companion object Factory {

        fun create(token: String?): LoginService {

            val builderOkHttp = OkHttpClient.Builder();

            val httpLoggingInterceptor = HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builderOkHttp.networkInterceptors().add(httpLoggingInterceptor)
            val retrofit: Retrofit

            if (!TextUtils.isEmpty(token)) {

                builderOkHttp.networkInterceptors().add(accessTokenProvidingInterceptor(token))
                val httpClient = builderOkHttp.build()
                retrofit = Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient)
                        .baseUrl("{Your own base URL}") //TODO:Add your own URL
                        .build()
            } else {

                retrofit = Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("{Your own base URL}") //TODO: Add your own URL
                        .build()
            }
            return retrofit.create(LoginService::class.java);

        }


        fun accessTokenProvidingInterceptor(token: String?) = Interceptor { chain ->
            val accessToken = token
            chain.proceed(chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build())
        }

    }
}