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
import com.music.stream.neptune.ui.screens.ArtistScreen
import com.music.stream.neptune.ui.screens.HomeScreen
import com.music.stream.neptune.ui.screens.LibraryScreen
import com.music.stream.neptune.ui.screens.PlayerScreen
import com.music.stream.neptune.ui.screens.SearchScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MyNavHost(
    navHostController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {

//    val context = LocalContext.current
//    var player : ExoPlayer? = null
//    player = ExoPlayer.Builder(context).build()

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
            SearchScreen(navHostController)
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

        composable("${Routes.Album.route}/{uString}") { navBackStackEntry ->
            /* Extracting the id from the route */
            val uId = navBackStackEntry.arguments?.getString("uString")
            /* We check if it's not null */
            uId?.let { id->
                AlbumScreen(navController = navHostController, albumName = id)
            }
        }

        composable("${Routes.Artist.route}/{aString}") { navBackStackEntry ->
            /* Extracting the id from the route */
            val aId = navBackStackEntry.arguments?.getString("aString")
            /* We check if it's not null */
            aId?.let { aid->
                ArtistScreen(navHostController, aid)
            }
        }
    }
}
