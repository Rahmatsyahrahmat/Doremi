package rahmatsyah.doremi.favorite.ui.track

import android.media.MediaPlayer
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.usecase.favorite.track.FavoriteTrackUseCase

class FavoriteTrackViewModel (private val favoriteTrackUseCase: FavoriteTrackUseCase): ViewModel(){

    private val mediaPlayer = MediaPlayer()
    val playedTrackPosition: MutableLiveData<Int> = MutableLiveData()

    init {
        playedTrackPosition.value = -1
    }

    fun getFavoriteTracks(): LiveData<List<Track>> = favoriteTrackUseCase.getFavoriteTracks().asLiveData()
    fun addToFavorite(track: Track){
        viewModelScope.launch {
            favoriteTrackUseCase.addToFavorite(track)
        }
    }

    fun playMediaPalyer(track: Track,position:Int){
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

    fun stopMediaPlayer(){
        playedTrackPosition.value = -1
        mediaPlayer.stop()
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }

    fun isPlaying() = mediaPlayer.isPlaying
}