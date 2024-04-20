package com.music.stream.neptune.ui.navigation

import androidx.annotation.DrawableRes
import com.music.stream.neptune.R

sealed class Routes(
    @DrawableRes val icon : Int = 0,
    val label : String,
    val route : String
) {
    object Home : Routes(icon = R.drawable.ic_home_filled, label = "Home", route = "home")
    object Search : Routes(icon = R.drawable.ic_search_big, label = "Search", route = "search")
    object Library : Routes(icon = R.drawable.ic_library_big, label = "Library", route = "library")
    object Album : Routes(0, "Album", "album")
    object Player : Routes(0, "Player", "player")
    object Artist : Routes(0, "Artist", "artist")
}