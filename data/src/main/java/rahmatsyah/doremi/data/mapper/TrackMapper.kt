package rahmatsyah.doremi.data.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rahmatsyah.doremi.data.sources.local.album.AlbumEntity
import rahmatsyah.doremi.data.sources.local.artist.ArtistEntity
import rahmatsyah.doremi.data.sources.local.track.TrackEntity
import rahmatsyah.doremi.data.sources.remote.response.TrackResponse
import rahmatsyah.doremi.domain.entity.Track


fun TrackResponse.toTrackEntity(isTop: Boolean = false):TrackEntity =
    TrackEntity(
        this.id,
        this.title,
        this.link,
        this.preview,
        this.artist.id,
        this.album?.id,
        isTop = isTop
    )

fun TrackResponse.toTrackEntityWithAlbumId(albumId:Int):TrackEntity =
    TrackEntity(
        this.id,
        this.title,
        this.link,
        this.preview,
        this.artist.id,
        albumId
    )

fun TrackEntity.toTrack():Track =
    Track(
        this.id,
        this.title,
        this.link,
        this.preview,
        isFavorite = this.isFavorite
    )

fun Track.toTrackEntity():TrackEntity =
    TrackEntity(
        this.id,
        this.title,
        this.link,
        this.preview,
        this.artist?.id,
        this.album?.id,
        this.isFavorite
    )

fun Flow<List<TrackEntity>>.toTrackList():Flow<List<Track>> =
    this.map {
        it.toTrackList()
    }

fun List<TrackEntity>.toTrackList():List<Track> =
    this.map {
        it.toTrack()
    }



fun List<TrackResponse>.toTrackEntities(isTop:Boolean = false):List<TrackEntity> =
    this.map {
        it.toTrackEntity(isTop)
    }

fun List<TrackResponse>.toTrackEntitiesWithAlbumId(albumId:Int):List<TrackEntity> =
    this.map {
        it.toTrackEntityWithAlbumId(albumId)
    }

fun List<TrackResponse>.getAlbumEntities(): List<AlbumEntity> =
    this.mapNotNull {
        it.album?.toAlbumEntity()
    }

fun List<TrackResponse>.getArtistEntities():List<ArtistEntity> =
    this.map {
        it.artist.toArtistEntity()
    }