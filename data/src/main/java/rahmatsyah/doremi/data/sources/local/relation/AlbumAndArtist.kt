package rahmatsyah.doremi.data.sources.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import rahmatsyah.doremi.data.sources.local.album.AlbumEntity
import rahmatsyah.doremi.data.sources.local.artist.ArtistEntity

data class AlbumAndArtist(
    @Embedded
    val album:AlbumEntity,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "id"
    )
    val artist:ArtistEntity?
)