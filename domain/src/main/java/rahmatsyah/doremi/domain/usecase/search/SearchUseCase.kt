package rahmatsyah.doremi.domain.usecase.search

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.entity.Track

interface SearchUseCase {
    fun searchAlbums(query: String): Flow<Result<List<Album>>>
    fun searchArtists(query: String): Flow<Result<List<Artist>>>
    fun searchTracks(query: String): Flow<Result<List<Track>>>
    suspend fun addTrackToFavorite(track: Track)
}