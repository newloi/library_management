package com.example.librarymanagement

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.librarymanagement.ui.navigation.AppNavHost

@Composable
fun MyApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController)
}