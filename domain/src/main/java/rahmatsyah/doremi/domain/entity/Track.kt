package rahmatsyah.doremi.domain.entity

data class Track(
    val id:Int,
    val title:String,
    val link:String?,
    val preview:String?,
    var artist:Artist? = null,
    var album: Album? = null,
    var isFavorite:Boolean,
    var isPlaying:Boolean = false
)