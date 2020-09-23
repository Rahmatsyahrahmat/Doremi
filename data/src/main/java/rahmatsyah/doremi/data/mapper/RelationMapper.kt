package rahmatsyah.doremi.data.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rahmatsyah.doremi.data.sources.local.relation.AlbumAndArtist
import rahmatsyah.doremi.data.sources.local.relation.TrackAndAlbumAndArtist
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Track

fun List<AlbumAndArtist>.toAlbumList(): List<Album> =
    this.map {
        val result = it.album.toAlbum()
        result.artist = it.artist?.toArtist()
        result
    }

fun Flow<List<AlbumAndArtist>>.toAlbumList(): Flow<List<Album>> =
    this.map {
        it.toAlbumList()
    }

fun Flow<AlbumAndArtist>.toAlbum(): Flow<Album> =
    this.map {
        val result = it.album.toAlbum()
        result.artist = it.artist?.toArtist()
        result
    }

fun TrackAndAlbumAndArtist.toTrack(): Track {
    val result = this.track.toTrack()
    result.album = this.album?.toAlbum()
    result.artist = this.artist?.toArtist()
    return result
}

fun Flow<List<TrackAndAlbumAndArtist>>.toTrackList(): Flow<List<Track>> =
    this.map {
        it.toTrackList()
    }

fun List<TrackAndAlbumAndArtist>.toTrackList(): List<Track> =
    this.map {
        it.toTrack()
    }