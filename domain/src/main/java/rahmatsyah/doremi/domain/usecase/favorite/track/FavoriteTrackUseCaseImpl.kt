package rahmatsyah.doremi.domain.usecase.favorite.track

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.repository.TrackRepository

class FavoriteTrackUseCaseImpl(private val trackRepository: TrackRepository) :
    FavoriteTrackUseCase {
    override fun getFavoriteTracks(): Flow<List<Track>> =
        trackRepository.getFavoriteTracks()

    override suspend fun addToFavorite(track: Track) =
        trackRepository.addToFavorite(track)
}