package rahmatsyah.doremi.data.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rahmatsyah.doremi.data.sources.local.album.AlbumEntity
import rahmatsyah.doremi.data.sources.local.artist.ArtistEntity
import rahmatsyah.doremi.data.sources.local.relation.AlbumAndArtist
import rahmatsyah.doremi.data.sources.remote.response.AlbumResponse
import rahmatsyah.doremi.domain.entity.Album

fun Album.toAlbumEntity():AlbumEntity =
    AlbumEntity(
        this.id,
        this.title,
        this.releaseDate,
        this.cover,
        this.artist?.id,
        this.isFavorite
    )

fun AlbumResponse.toAlbumEntity(isTop:Boolean = false):AlbumEntity =
    AlbumEntity(
        this.id,
        this.title,
        this.releasedDate,
        this.cover,
        this.artist?.id,
        isTop = isTop
    )

fun AlbumResponse.toAlbumEntityWithArtistId(artistId: Int):AlbumEntity =
    AlbumEntity(
        this.id,
        this.title,
        this.releasedDate,
        this.cover,
        artistId
    )

fun AlbumEntity.toAlbum():Album =
    Album(
        this.id,
        this.title,
        this.releaseDate,
        this.cover,
        isFavorite = this.isFavorite
    )

fun List<AlbumResponse>.toAlbumEntities(isTop: Boolean = false):List<AlbumEntity> =
    this.map {
        it.toAlbumEntity(isTop)
    }

fun List<AlbumResponse>.toAlbumEntitiesWithArtistId(artistId:Int):List<AlbumEntity> =
    this.map {
        it.toAlbumEntityWithArtistId(artistId)
    }

fun List<AlbumResponse>.getArtistEntities():List<ArtistEntity> =
    this.mapNotNull {
        it.artist?.toArtistEntity()
    }


