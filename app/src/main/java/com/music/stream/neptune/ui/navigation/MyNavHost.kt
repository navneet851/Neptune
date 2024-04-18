package com.music.stream.neptune.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.music.stream.neptune.ui.screens.AlbumScreen
import com.music.stream.neptune.ui.screens.HomeScreen
import com.music.stream.neptune.ui.screens.LibraryScreen
import com.music.stream.neptune.ui.screens.PlayerScreen
import com.music.stream.neptune.ui.screens.SearchScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MyNavHost(navHostController: NavHostController, bottomBarState : MutableState<Boolean>) {

    NavHost(navController = navHostController, startDestination = Routes.Home.route){
        composable(Routes.Home.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
                HomeScreen(navHostController)
        }
        composable(Routes.Search.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            SearchScreen()
        }
        composable(Routes.Library.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            LibraryScreen()
        }
        composable(Routes.Player.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            PlayerScreen(navHostController)
        }

        composable("album/{uId}") { navBackStackEntry ->
            /* Extracting the id from the route */
            val uId = navBackStackEntry.arguments?.getString("uId")
            /* We check if it's not null */
            uId?.let { id->
                AlbumScreen(navHostController, id)
            }
        }
//        composable(
//            route = "album/{albumIndex}",
//            arguments = listOf(
//                navArgument("albumIndex") {
//                    type = NavType.IntType
//                }
//            )
//        ){
//            LaunchedEffect(Unit){
//                bottomBarState.value = true
//            }
//            AlbumScreen(
//                navHostController,
//                it.arguments!!.getInt("albumIndex")
//                )
//        }
    }
}
