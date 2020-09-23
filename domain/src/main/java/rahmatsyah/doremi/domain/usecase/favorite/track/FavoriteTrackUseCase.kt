package rahmatsyah.doremi.domain.usecase.favorite.track

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.entity.Track

interface FavoriteTrackUseCase {
    fun getFavoriteTracks(): Flow<List<Track>>
    suspend fun addToFavorite(track: Track)
}