package com.music.stream.neptune.components.bottombar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.music.stream.neptune.screens.HomeScreen
import com.music.stream.neptune.screens.LibraryScreen
import com.music.stream.neptune.screens.PlayerScreen
import com.music.stream.neptune.screens.SearchScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MyNavHost(navHostController: NavHostController, bottomBarState : MutableState<Boolean>) {

    NavHost(navController = navHostController, startDestination = BottomNavItem.Home.route){
        composable(BottomNavItem.Home.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
                HomeScreen(navHostController)
        }
        composable(BottomNavItem.Search.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            SearchScreen()
        }
        composable(BottomNavItem.Library.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            LibraryScreen()
        }
        composable("Liked Songs"){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            PlayerScreen(navHostController)
        }
    }
}
