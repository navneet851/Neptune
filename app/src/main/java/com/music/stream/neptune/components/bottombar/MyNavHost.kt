package com.music.stream.neptune.components.bottombar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.music.stream.neptune.HomeScreen
import com.music.stream.neptune.LibraryScreen
import com.music.stream.neptune.SearchScreen

@Composable
fun MyNavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = BottomNavItem.Home.route){
        composable(BottomNavItem.Home.route){
            HomeScreen()
        }
        composable(BottomNavItem.Search.route){
            SearchScreen()
        }
        composable(BottomNavItem.Library.route){
            LibraryScreen()
        }

    }
}
