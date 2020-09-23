package rahmatsyah.doremi.data.sources.local.track

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackEntity(
    @PrimaryKey
    val id:Int,
    val title:String,
    val link:String?,
    val preview:String?,
    val artistId:Int?,
    val albumId:Int?,
    var isFavorite:Boolean = false,
    var isTop:Boolean = false
)