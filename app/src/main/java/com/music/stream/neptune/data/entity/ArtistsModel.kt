package com.music.stream.neptune.data.entity

data class ArtistsModel(
    val name : String,
    val coverUri : String
){
    constructor() : this("", "")
}
