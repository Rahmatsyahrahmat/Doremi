package rahmatsyah.doremi.app.ui.artist

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.usecase.atist.ArtistUseCase

class ArtistViewModel(private val artistUseCase: ArtistUseCase) : ViewModel() {
    private val artistId: MutableLiveData<Int> = MutableLiveData()

    val artist: LiveData<Result<Artist>> = artistId.switchMap {
        artistUseCase.getArtist(it).asLiveData()
    }

    val albums: LiveData<Result<List<Album>>> = artistId.switchMap {
        artistUseCase.getArtistAlbums(it).asLiveData()
    }

    fun setArtistId(artistId: Int) {
        this.artistId.value = artistId
    }

    fun addToFavorite() {
        viewModelScope.launch {
            artist.value?.data?.let { artistUseCase.addToFavorite(it) }
        }
    }
}