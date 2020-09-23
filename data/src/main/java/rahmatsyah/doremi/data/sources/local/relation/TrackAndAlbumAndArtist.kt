package rahmatsyah.doremi.data.sources.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import rahmatsyah.doremi.data.sources.local.album.AlbumEntity
import rahmatsyah.doremi.data.sources.local.artist.ArtistEntity
import rahmatsyah.doremi.data.sources.local.track.TrackEntity

data class TrackAndAlbumAndArtist(
    @Embedded
    val track:TrackEntity,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "id"
    )
    val album:AlbumEntity?,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "id"
    )
    val artist:ArtistEntity?
)