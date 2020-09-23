package rahmatsyah.doremi.domain.usecase.atist

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.repository.ArtistRepository

class ArtistUseCaseImpl(private val artistRepository: ArtistRepository):ArtistUseCase{
    override fun getArtist(id: Int): Flow<Result<Artist>> =
        artistRepository.getArtist(id)

    override fun getArtistAlbums(id: Int): Flow<Result<List<Album>>> =
        artistRepository.getArtistAlbums(id)

    override suspend fun addToFavorite(artist: Artist) {
        artistRepository.addToFavorite(artist)
    }

}