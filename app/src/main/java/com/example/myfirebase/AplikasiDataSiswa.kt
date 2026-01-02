package com.example.myfirebase

import android.app.Application
import com.example.myfirebase.repositori.AppContainer
import com.example.myfirebase.repositori.ContainerApp

class AplikasiDataSiswa : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ContainerApp()
    }
}
