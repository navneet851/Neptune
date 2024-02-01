package com.music.stream.neptune.components.bottombar

import androidx.annotation.DrawableRes
import com.music.stream.neptune.R

sealed class BottomNavItem(
    @DrawableRes val icon : Int = 0,
    val label : String,
    val route : String
) {
    object Home : BottomNavItem(icon = R.drawable.ic_home_filled, label = "Home", route = "home")
    object Search : BottomNavItem(icon = R.drawable.ic_search_big, label = "Search", route = "search")
    object Library : BottomNavItem(icon = R.drawable.ic_library_big, label = "Library", route = "library")
}