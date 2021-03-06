package rahmatsyah.doremi.search.ui

import android.media.MediaPlayer
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.usecase.search.SearchUseCase

class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val mediaPlayer = MediaPlayer()
    val playedTrackPosition: MutableLiveData<Int> = MutableLiveData(-1)

    private val query: MutableLiveData<String> = MutableLiveData()

    private val albums: LiveData<Result<List<Album>>> = query.switchMap {
        searchUseCase.searchAlbums(it).asLiveData()
    }
    private val artists: LiveData<Result<List<Artist>>> = query.switchMap {
        searchUseCase.searchArtists(it).asLiveData()
    }
    private val tracks: LiveData<Result<List<Track>>> = query.switchMap {
        searchUseCase.searchTracks(it).asLiveData()
    }

    private val isAlbumEmpty: LiveData<Boolean> = albums.map {
        it.data.isNullOrEmpty()
    }

    private val isArtistEmpty: LiveData<Boolean> = artists.map {
        it.data.isNullOrEmpty()
    }

    private val isTrackEmpty: LiveData<Boolean> = tracks.map {
        it.data.isNullOrEmpty()
    }

    private val isResultEmpty: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>()

    fun setQuery(query: String) {
        this.query.value = query
    }

    fun getAlbums() = albums

    fun getArtists() = artists

    fun getTracks() = tracks

    fun isResultEmpty(): LiveData<Boolean> {
        isResultEmpty.value = false
        isResultEmpty.addSource(isAlbumEmpty) {
            isResultEmpty.value = it || isResultEmpty.value ?: false
        }
        isResultEmpty.addSource(isArtistEmpty) {
            isResultEmpty.value = it || isResultEmpty.value ?: false
        }
        isResultEmpty.addSource(isTrackEmpty) {
            isResultEmpty.value = it || isResultEmpty.value ?: false
        }
        return isResultEmpty
    }

    fun addTrackToFavorite(track: Track) {
        viewModelScope.launch {
            searchUseCase.addTrackToFavorite(track)
        }
    }

    fun playMediaPalyer(track: Track, position: Int) {
        playedTrackPosition.value = position
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.apply {
            setDataSource(track.preview)
            prepare()
            setOnCompletionListener {
                playedTrackPosition.value = -1
                stop()
                reset()
            }
            start()
        }
    }

    fun isPlaying() = mediaPlayer.isPlaying

    fun stopMediaPlayer() {
        playedTrackPosition.value = -1
        mediaPlayer.stop()
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }

}