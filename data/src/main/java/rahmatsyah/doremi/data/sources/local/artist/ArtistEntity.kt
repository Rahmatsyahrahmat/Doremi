package rahmatsyah.doremi.data.sources.local.artist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtistEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val picture: String?,
    var isFavorite: Boolean = false,
    var isTop: Boolean = false
)