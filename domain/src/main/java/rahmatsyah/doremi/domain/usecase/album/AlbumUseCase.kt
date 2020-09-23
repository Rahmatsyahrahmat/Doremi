package rahmatsyah.doremi.domain.usecase.album

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Track

interface AlbumUseCase {
    fun getAlbum(id: Int): Flow<Result<Album>>
    fun getAlbumTracks(id: Int): Flow<Result<List<Track>>>
    suspend fun addToFavorite(album: Album)
    suspend fun addTrackToFavorite(track: Track)
}