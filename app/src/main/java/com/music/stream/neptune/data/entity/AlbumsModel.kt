package com.music.stream.neptune.data.entity

data class AlbumsModel(
    val artists : String,
    val coverUri : String,
    val name : String,
){
    constructor() : this("" ,"" ,"")
}
