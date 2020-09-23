package rahmatsyah.doremi.domain.usecase.favorite.artist

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.repository.ArtistRepository

class FavoriteArtistUseCaseImpl(private val artistRepository: ArtistRepository):FavoriteArtistUseCase {
    override fun getFavoriteArtists(): Flow<List<Artist>> =
        artistRepository.getFavoriteArtists()
}