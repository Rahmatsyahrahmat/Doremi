package rahmatsyah.doremi.domain.repository

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Track

interface AlbumRepository {
    fun getAlbum(id: Int): Flow<Result<Album>>
    fun getAlbumTracks(id: Int): Flow<Result<List<Track>>>
    fun getTopAlbums(): Flow<Result<List<Album>>>
    fun getFavoriteAlbums(): Flow<List<Album>>
    fun searchAlbums(query: String): Flow<Result<List<Album>>>
    suspend fun addToFavorite(album: Album)
}