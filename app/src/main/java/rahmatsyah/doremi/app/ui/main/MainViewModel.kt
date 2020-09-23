package rahmatsyah.doremi.app.ui.main

import android.media.MediaPlayer
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.usecase.main.MainUseCase

class MainViewModel(private val mainUseCase: MainUseCase) : ViewModel() {
    val topAlbums: LiveData<Result<List<Album>>> = mainUseCase.getTopAlbums().asLiveData()
    val topArtists: LiveData<Result<List<Artist>>> = mainUseCase.getTopArtists().asLiveData()
    val topTracks: LiveData<Result<List<Track>>> = mainUseCase.getTopTracks().asLiveData()

    private val mediaPlayer = MediaPlayer()
    val playedTrackPosition: MutableLiveData<Int> = MutableLiveData(-1)

    fun addTrackToFavorite(track: Track) {
        viewModelScope.launch {
            mainUseCase.addTrackToFavorite(track)
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