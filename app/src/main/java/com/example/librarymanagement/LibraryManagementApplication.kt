package com.example.librarymanagement

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.librarymanagement.data.AppContainer
import com.example.librarymanagement.data.AppDataContainer
import com.example.librarymanagement.ui.navigation.AppNavHost

class LibraryManagementApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}

@Composable
fun MyApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController)
}