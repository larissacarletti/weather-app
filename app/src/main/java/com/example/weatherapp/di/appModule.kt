package com.example.weatherapp.di

import com.example.weatherapp.data.location.DefaultLocationTracker
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.presentation.WeatherViewModel
import com.google.android.gms.location.LocationServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    ).build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    single { LocationServices.getFusedLocationProviderClient(androidApplication()) }

    single<LocationTracker> { DefaultLocationTracker(get(), androidApplication()) }

    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

    viewModel { WeatherViewModel(get(), get()) }
}

