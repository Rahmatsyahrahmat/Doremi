package rahmatsyah.doremi.domain.usecase.favorite.album

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.entity.Album

interface FavoriteAlbumUseCase {
    fun getFavoriteAlbum(): Flow<List<Album>>
}