package com.music.stream.neptune.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.music.stream.neptune.R

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun SearchScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize().statusBarsPadding()){
        item{
            SearchTopBar()
        }
        stickyHeader {
            SearchStickyBar()
        }
        items(20){
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
                ){

                Row(horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(200.dp)) {
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

                Icon(imageVector = Icons.Default.MoreVert, tint = Color.Gray, contentDescription = "")
            }
        }
    }
}

@Composable
fun SearchTopBar() {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Search", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Icon(imageVector = Icons.Default.Person, contentDescription = "", tint = Color.White)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SearchStickyBar() {
    var text by remember {
        mutableStateOf("")
    }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(55.dp)
            .background(Color.White)
            .padding(10.dp, 0.dp)
    ){
        Icon(
            painterResource(id = R.drawable.ic_search_big),
            tint = Color.Black,
            contentDescription = "")

        TextField(
            enabled = true,
            value = text,
            textStyle = TextStyle.Default.copy(fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight(500)),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            onValueChange = {
                text = it
            },
            placeholder = {
                Text(
                     textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    text = "What do you want to listen to?"

                )
            }
        )
    }
}
