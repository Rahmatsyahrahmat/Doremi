package rahmatsyah.doremi.app.ui.album

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_album.*
import org.koin.android.viewmodel.ext.android.viewModel
import rahmatsyah.doremi.app.R
import rahmatsyah.doremi.app.ui.artist.ArtistActivity
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album

class AlbumActivity : AppCompatActivity() {

    companion object {
        const val ALBUM_ID_EXTRAS = "album_id_extras"
    }

    private val viewModel:AlbumViewModel by viewModel()

    private val trackAdapter:AlbumTrackAdapter by lazy {
        AlbumTrackAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        setUpView()

        val albumId = intent.getIntExtra(ALBUM_ID_EXTRAS,0)
        viewModel.setAlbumId(albumId)

        viewModel.album.observe(this, Observer {result->
            when(result){
                is Result.Success ->{
                    result.data?.let { album->
                        initAlbum(album)
                    }
                }
                is Result.Error ->{
                    Toast.makeText(this,result.message.toString(),Toast.LENGTH_LONG).show()
                }
                is Result.Loading ->{
                    result.data?.let {album->
                        initAlbum(album)
                    }
                }
            }
        })

        viewModel.tracks.observe(this, Observer {result->
            when(result){
                is Result.Success ->{
                    tracksProgress.visibility = View.GONE
                    result.data?.let { trackAdapter.setTracks(it) }
                    viewModel.playedTrackPosition.observe(this, Observer {
                        trackAdapter.setPlayingOn(it)
                    })
                }
                is Result.Error ->{
                    tracksProgress.visibility = View.GONE
                    Toast.makeText(this,result.message.toString(),Toast.LENGTH_LONG).show()
                }
                is Result.Loading ->{
                    tracksProgress.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun setUpView() {
        albumTracks.adapter = trackAdapter

        trackAdapter.setOnItemAddToFavoriteListener {
            viewModel.addTrackToFavorite(it)
        }

        trackAdapter.setOnItemListenMoreClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it)
            startActivity(intent)
        }

        trackAdapter.setOnItemPlayListener{track, position ->
            if (track.isPlaying){
                viewModel.stopMediaPlayer()
                trackAdapter.stop(position)
            }else {
                trackAdapter.play(position)
                viewModel.playMediaPalyer(track, position)
            }
        }

        albumFavorite.setOnClickListener {
            viewModel.addToFavorite()
        }
    }

    private fun initAlbum(album:Album){
        Glide.with(this).load(album.cover).into(albumCover)
        albumName.text = album.title
        albumReleasedDate.text = resources.getString(R.string.released_date,album.releaseDate)

        Glide.with(this).load(album.artist?.picture).into(albumArtistsPicture)
        albumArtistsName.text = album.artist?.name

        album.isFavorite.apply {
            if (this){
                Glide.with(this@AlbumActivity).load(R.drawable.ic_favorite_red).into(albumFavorite)
            }else{
                Glide.with(this@AlbumActivity).load(R.drawable.ic_favorite_border_black).into(albumFavorite)
            }
        }

        albumArtistsName.setOnClickListener {
            album.artist?.id?.let { artistId ->
                startArtitsActivity(artistId)
            }
        }
        albumArtistsPicture.setOnClickListener {
            album.artist?.id?.let { artistId -> startArtitsActivity(artistId) }
        }
    }

    private fun startArtitsActivity(artistId:Int){
        val intent = Intent(this,ArtistActivity::class.java)
        intent.putExtra(ArtistActivity.ARTIST_ID_EXTRAS,artistId)
        startActivity(intent)
    }

    override fun startActivity(intent: Intent?) {
        if (viewModel.isPlaying()){
            viewModel.stopMediaPlayer()
        }
        super.startActivity(intent)
    }
}