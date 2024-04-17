package com.music.stream.neptune.data.entity

data class AlbumsModel(
    val id : Int,
    val artists : String,
    val coverUri : String,
    val name : String,
    val time : String
){
    constructor() : this( -1,"" ,"" ,"", "")
}
