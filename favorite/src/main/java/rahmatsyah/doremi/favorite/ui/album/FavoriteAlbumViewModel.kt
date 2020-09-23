package rahmatsyah.doremi.favorite.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.usecase.favorite.album.FavoriteAlbumUseCase

class FavoriteAlbumViewModel(private val favoriteAlbumUseCase: FavoriteAlbumUseCase) : ViewModel() {
    fun getFavoriteAlbums(): LiveData<List<Album>> =
        favoriteAlbumUseCase.getFavoriteAlbum().asLiveData()
}