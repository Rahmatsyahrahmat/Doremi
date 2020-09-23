package rahmatsyah.doremi.domain.repository

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Track

interface TrackRepository {
    fun getTopTracks():Flow<Result<List<Track>>>
    fun getFavoriteTracks():Flow<List<Track>>
    fun searchTracks(query:String):Flow<Result<List<Track>>>
    suspend fun addToFavorite(track: Track)
}