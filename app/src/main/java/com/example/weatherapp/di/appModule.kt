package com.example.weatherapp.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    single{
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(
                OkHttpClient().Builder()


            )
    }


}