package com.music.stream.neptune.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.music.stream.neptune.ui.screens.AlbumScreen
import com.music.stream.neptune.ui.screens.ArtistScreen
import com.music.stream.neptune.ui.screens.HomeScreen
import com.music.stream.neptune.ui.screens.LibraryScreen
import com.music.stream.neptune.ui.screens.PlayerScreen
import com.music.stream.neptune.ui.screens.SearchScreen
import com.music.stream.neptune.ui.viewmodel.PlayerViewModel

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MyNavHost(
    navHostController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    bottomBarPlayerState: MutableState<Boolean>
) {

    val playerViewModel : PlayerViewModel = hiltViewModel()
    val playerState by playerViewModel.currentSongTitle

    Log.d("player", playerState.toString())

//    val context = LocalContext.current
//    var player : ExoPlayer? = null
//    player = ExoPlayer.Builder(context).build()

    NavHost(navController = navHostController, startDestination = Routes.Home.route){
        composable(Routes.Home.route){
            LaunchedEffect(playerState) {
                bottomBarState.value = true
                bottomBarPlayerState.value = playerState != ""
            }
                HomeScreen(navHostController)
        }
        composable(Routes.Search.route){
            LaunchedEffect(playerState) {
                bottomBarState.value = true
                bottomBarPlayerState.value = playerState != ""
            }
            SearchScreen(navHostController)
        }
        composable(Routes.Library.route) {
            LaunchedEffect(playerState) {
                bottomBarState.value = true
                bottomBarPlayerState.value = playerState != ""
            }
            LibraryScreen()
        }
        composable("${Routes.Player.route}/{pString}"){navBackStackEntry ->
            LaunchedEffect(playerState) {
                bottomBarState.value = false
                bottomBarPlayerState.value = playerState != ""
            }
            /* Extracting the id from the route */
            val pId = navBackStackEntry.arguments?.getString("pString")
            /* We check if it's not null */
            pId?.let { id->
            PlayerScreen(navHostController, id)
            }
        }

        composable("${Routes.Album.route}/{uString}") { navBackStackEntry ->
            LaunchedEffect(playerState) {
                bottomBarState.value = true
                bottomBarPlayerState.value = playerState != ""
            }

            /* Extracting the id from the route */
            val uId = navBackStackEntry.arguments?.getString("uString")
            /* We check if it's not null */
            uId?.let { id->
                AlbumScreen(navController = navHostController, albumName = id)
            }
        }

        composable("${Routes.Artist.route}/{aString}") { navBackStackEntry ->
            LaunchedEffect(playerState) {
                bottomBarState.value = true
                bottomBarPlayerState.value = playerState != ""
            }

            /* Extracting the id from the route */
            val aId = navBackStackEntry.arguments?.getString("aString")
            /* We check if it's not null */
            aId?.let { aid->
                ArtistScreen(navHostController, aid)
            }
        }
    }
}
