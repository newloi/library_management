package com.example.librarymanagement

import android.app.Application
import com.example.librarymanagement.data.AppContainer
import com.example.librarymanagement.data.AppDataContainer

class LibraryManagementApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}

