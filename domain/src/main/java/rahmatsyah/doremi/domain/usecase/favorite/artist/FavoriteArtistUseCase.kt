package rahmatsyah.doremi.domain.usecase.favorite.artist

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.entity.Artist

interface FavoriteArtistUseCase {
    fun getFavoriteArtists():Flow<List<Artist>>
}