package rahmatsyah.doremi.domain.usecase.main

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.entity.Track

interface MainUseCase {
    fun getTopAlbums(): Flow<Result<List<Album>>>
    fun getTopArtists(): Flow<Result<List<Artist>>>
    fun getTopTracks(): Flow<Result<List<Track>>>
    suspend fun addTrackToFavorite(track: Track)
}