package rahmatsyah.doremi.data.sources.local.album

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val releaseDate: String?,
    val cover: String?,
    val artistId: Int?,
    var isFavorite: Boolean = false,
    var isTop: Boolean = false
)