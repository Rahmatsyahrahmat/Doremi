package rahmatsyah.doremi.domain.usecase.favorite.album

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.repository.AlbumRepository

class FavoriteAlbumUseCaseImpl(private val albumRepository: AlbumRepository) :
    FavoriteAlbumUseCase {
    override fun getFavoriteAlbum(): Flow<List<Album>> =
        albumRepository.getFavoriteAlbums()
}