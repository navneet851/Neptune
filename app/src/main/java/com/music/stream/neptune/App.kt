package com.music.stream.neptune

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.music.stream.neptune.ui.navigation.MainBottomNavigation
import com.music.stream.neptune.ui.navigation.MyNavHost
import com.music.stream.neptune.ui.navigation.Routes


@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App() {
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val bottomBarPlayerState = rememberSaveable { (mutableStateOf(true)) }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        Routes.Home.route -> {
            bottomBarState.value = true
        }
        Routes.Search.route -> {
            bottomBarState.value = true
        }
        Routes.Library.route -> {
            bottomBarState.value = true
        }
        Routes.Album.route -> {
            bottomBarState.value = false
        }
    }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
        ,
        bottomBar = {
            MainBottomNavigation(navController = navController, bottomBarState = bottomBarState, bottomBarPlayerState)
        }
    ) {
        MyNavHost(navHostController = navController, bottomBarState = bottomBarState, bottomBarPlayerState)
    }
}


