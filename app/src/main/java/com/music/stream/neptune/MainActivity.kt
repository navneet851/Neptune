package com.music.stream.neptune

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.music.stream.neptune.ui.theme.NeptuneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?){
//        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.light(
//                Color.TRANSPARENT,
//                Color.TRANSPARENT
//            )
//        )
        super.onCreate(savedInstanceState)

        setContent {
            NeptuneTheme {
                // A surface container using the 'background' color from the theme
                    App()


            }
        }
    }
}



