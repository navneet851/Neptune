package com.music.stream.neptune.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.music.stream.neptune.R

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen() {
    Scaffold( modifier = Modifier.background(Color.Gray),
        topBar = {
                TopAppBar(
                    title = {
                    Text(text = "Your Library", fontWeight = FontWeight.Bold, color = Color.White)
                },
                    navigationIcon = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                modifier = Modifier.size(35.dp),
                                tint = Color.White,
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "Localized description"
                            )
                        }
                    }
            )
        }
    ) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(it)
        ) {
                repeat(10){

                        Row(horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 10.dp)
                        ){
                            Image(modifier = Modifier
                                .size(60.dp),
                                painter = painterResource(id = R.drawable.album),
                                contentScale = ContentScale.Crop,
                                contentDescription = "")
                            Column(modifier = Modifier.padding(start = 10.dp)) {
                                Text(text = "Album name", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold )
                                Text(text = "Song singer Name", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                }
        }
    }
}