package rahmatsyah.doremi.app.ui.album

import android.media.MediaPlayer
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.usecase.album.AlbumUseCase

class AlbumViewModel(private val albumUseCase: AlbumUseCase):ViewModel(){

    private val albumId:MutableLiveData<Int> = MutableLiveData(-1)

    private val mediaPlayer = MediaPlayer()
    val playedTrackPosition:MutableLiveData<Int> = MutableLiveData()

    val album:LiveData<Result<Album>> = albumId.switchMap {
        albumUseCase.getAlbum(it).asLiveData()
    }

    val tracks:LiveData<Result<List<Track>>> = albumId.switchMap {
        albumUseCase.getAlbumTracks(it).asLiveData()
    }

    fun setAlbumId(albumId:Int){
        this.albumId.value = albumId
    }

    fun addToFavorite(){
        viewModelScope.launch {
            album.value?.data?.let { albumUseCase.addToFavorite(it) }
        }
    }

    fun addTrackToFavorite(track: Track){
        viewModelScope.launch {
            albumUseCase.addTrackToFavorite(track)
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