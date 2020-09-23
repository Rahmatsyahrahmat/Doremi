package rahmatsyah.doremi.domain.usecase.atist

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist

interface ArtistUseCase {
    fun getArtist(id: Int): Flow<Result<Artist>>
    fun getArtistAlbums(id: Int): Flow<Result<List<Album>>>
    suspend fun addToFavorite(artist: Artist)
}