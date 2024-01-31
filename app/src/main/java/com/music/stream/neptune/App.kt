package com.music.stream.neptune

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.music.stream.neptune.components.bottombar.MainBottomNavigation
import com.music.stream.neptune.components.bottombar.MyNavHost

@Preview()
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun App() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            MainBottomNavigation(navController = navController)
        }
    ) {
        MyNavHost(navHostController = navController)
    }
}


