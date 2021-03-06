package rahmatsyah.doremi.favorite.ui.track

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_favorite_track.*
import kotlinx.android.synthetic.main.view_empty.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import rahmatsyah.doremi.app.ui.album.AlbumActivity
import rahmatsyah.doremi.app.ui.main.adapter.TrackAdapter
import rahmatsyah.doremi.favorite.R
import rahmatsyah.doremi.favorite.di.favoriteViewModelModule

class FavoriteTrackActivity : AppCompatActivity() {

    private val viewModel: FavoriteTrackViewModel by viewModel()

    private val trackAdapter: TrackAdapter by lazy {
        TrackAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_track)

        loadKoinModules(favoriteViewModelModule)

        favoriteTracks.adapter = trackAdapter

        trackAdapter.setOnItemAddToFavoriteListener {
            viewModel.addToFavorite(it)
        }

        trackAdapter.setOnItemListenMoreClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it)
            startActivity(intent)
        }

        trackAdapter.setOnItemPlayListener { track, position ->
            if (track.isPlaying) {
                viewModel.stopMediaPlayer()
                trackAdapter.stop(position)
            } else {
                trackAdapter.play(position)
                viewModel.playMediaPalyer(track, position)
            }
        }

        trackAdapter.setOnItemClickListener {
            if (viewModel.isPlaying()) {
                viewModel.stopMediaPlayer()
            }
            val intent = Intent(this, AlbumActivity::class.java)
            intent.putExtra(AlbumActivity.ALBUM_ID_EXTRAS, it)
            startActivity(intent)
        }

        viewModel.getFavoriteTracks().observe(this, { tracks ->
            if (tracks.isNullOrEmpty()) {
                emptyList.visibility = View.VISIBLE
                emptyMessage.text = getString(R.string.no_list_of_favorite_tracks)
            }
            trackAdapter.setTracks(tracks)
            viewModel.playedTrackPosition.observe(this, {
                trackAdapter.setPlayingOn(it)
            })
        })

    }
}