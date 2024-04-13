package com.music.stream.neptune.data.entity

data class SongsModel(
    val id : Int,
    val title : String,
    val album : String,
    val singer : String,
    val coverUri : String,
    val url : String
){
    constructor() : this(-1 ,"" ,"" ,"" ,"" ,"")
}
