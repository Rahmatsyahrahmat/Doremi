package rahmatsyah.doremi.favorite.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.usecase.favorite.artist.FavoriteArtistUseCase

class FavoriteArtistViewModel (private val favoriteArtistUseCase: FavoriteArtistUseCase):ViewModel(){
    fun getFavoriteArtists():LiveData<List<Artist>> = favoriteArtistUseCase.getFavoriteArtists().asLiveData()
}