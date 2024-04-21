package com.music.stream.neptune.di

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class Palette {
//    fun extractColorFromImageUrl(context: Context, imageUrl: String, onPaletteGenerated: (Palette) -> Unit) {
//        Glide.with(context)
//            .asBitmap()
//            .load(imageUrl)
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    Palette.from(resource).generate { palette ->
//                        palette?.let { onPaletteGenerated(it) }
//                    }
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // Handle cleanup here if necessary
//                }
//            })
//    }

//    fun extractDominantColorFromImageUrl(context: Context, imageUrl: String, onColorExtracted: (Color) -> Unit) {
//        Glide.with(context)
//            .asBitmap()
//            .load(imageUrl)
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    Palette.from(resource).generate { palette ->
//                        val dominantColor = palette?.dominantSwatch?.rgb
//                        dominantColor?.let {
//                            // Convert RGB color integer to ARGB color integer with full opacity
//                            val argbColor = Color(it or (0xFF shl 24))
//                            onColorExtracted(argbColor)
//                        }
//                    }
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // Handle cleanup here if necessary
//                }
//            })
//    }
    fun extractLightVibrantColorFromImageUrl(context: Context, imageUrl: String, onColorExtracted: (Color) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Create a scaled down version of the bitmap
                    val scaledBitmap = Bitmap.createScaledBitmap(resource, 50, 50, true)

                    Palette
                        .from(scaledBitmap)
                        .generate { palette ->
                        val lightVibrantColor = palette?.mutedSwatch?.rgb
                        lightVibrantColor?.let {
                            // Convert RGB color integer to ARGB color integer with full opacity
                            val argbColor = Color(it or (0xFF shl 24))
                            onColorExtracted(argbColor)
                        }
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle cleanup here if necessary
                }
            })
    }
}