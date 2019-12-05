package com.example.qurbatask.application

import android.app.Application
import com.example.qurbatask.main.MainViewModel
import com.example.qurbatask.network.ApiServices
import com.example.qurbatask.network.BASE_URL
import com.example.qurbatask.network.NetworkConfig
import com.example.qurbatask.network.RemoteDataSource
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class app : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        startKoin {
            androidContext(this@app)
            modules(
                listOf(
                    module {

                        single { RemoteDataSource() }
                        viewModel { MainViewModel(get()) }
                    }
                )
            )
        }
    }

}