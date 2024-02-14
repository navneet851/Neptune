package com.music.stream.neptune

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.music.stream.neptune.components.bottombar.MainBottomNavigation
import com.music.stream.neptune.components.bottombar.MyNavHost

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun App() {
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        "home" -> {
            bottomBarState.value = true
        }
        "search" -> {
            bottomBarState.value = true
        }
        "library" -> {
            bottomBarState.value = true
        }
        "Liked Songs" -> {
            bottomBarState.value = false
        }
    }

    Scaffold(
        bottomBar = {
            MainBottomNavigation(navController = navController, bottomBarState = bottomBarState)
        }
    ) {
        MyNavHost(navHostController = navController, bottomBarState = bottomBarState)
    }
}


