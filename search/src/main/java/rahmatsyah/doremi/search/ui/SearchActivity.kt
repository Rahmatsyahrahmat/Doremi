package rahmatsyah.doremi.search.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import rahmatsyah.doremi.app.ui.album.AlbumActivity
import rahmatsyah.doremi.app.ui.artist.ArtistActivity
import rahmatsyah.doremi.app.ui.main.adapter.AlbumAdapter
import rahmatsyah.doremi.app.ui.main.adapter.ArtistAdapter
import rahmatsyah.doremi.app.ui.main.adapter.TrackAdapter
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.search.R
import rahmatsyah.doremi.search.di.searchViewModelModul

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()

    private val albumAdapter: AlbumAdapter by lazy {
        AlbumAdapter(this)
    }
    private val artistAdapter: ArtistAdapter by lazy {
        ArtistAdapter(this)
    }
    private val trackAdapter: TrackAdapter by lazy {
        TrackAdapter(this)
    }

    private val textWatceh = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = Unit
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            lifecycleScope.launch {
                delay(1000)
                viewModel.setQuery(p0.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        loadKoinModules(searchViewModelModul)

        setUpView()

        viewModel.getAlbums().observe(this, { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let {
                        albumAdapter.setAlbums(it)
                        if (it.isNotEmpty())
                            showAlbumsResult(true)
                    }
                }
                is Result.Error -> {
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showAlbumsResult(false)
                }
            }
        })
        viewModel.getArtists().observe(this, { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let {
                        artistAdapter.setArtists(it)
                        if (it.isNotEmpty())
                            showArtistsResult(true)
                    }
                }
                is Result.Error -> {
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showArtistsResult(false)
                }
            }
        })
        viewModel.getTracks().observe(this, { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let {
                        trackAdapter.setTracks(it)
                        if (it.isNotEmpty())
                            showTracksResult(true)
                    }
                    viewModel.playedTrackPosition.observe(this, {
                        trackAdapter.setPlayingOn(it)
                    })
                }
                is Result.Error -> {
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showTracksResult(false)
                }
            }
        })

        viewModel.isResultEmpty().observe(this, {
            emptyResult.visibility = viewVisibilityState(it)
            if (it) {
                searchProgress.visibility = View.GONE
            }
        })

    }

    private fun setUpView() {
        searchField.requestFocus()

        searchAlbumsList.adapter = albumAdapter
        searchArtistsList.adapter = artistAdapter
        searchTracksList.adapter = trackAdapter

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
            val intent = Intent(this, AlbumActivity::class.java)
            intent.putExtra(AlbumActivity.ALBUM_ID_EXTRAS, it)
            startActivity(intent)
        }

        trackAdapter.setOnItemAddToFavoriteListener {
            viewModel.addTrackToFavorite(it)
        }

        trackAdapter.setOnItemListenMoreClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it)
            startActivity(intent)
        }

        albumAdapter.setOnItemClickListener {
            val intent = Intent(this, AlbumActivity::class.java)
            intent.putExtra(AlbumActivity.ALBUM_ID_EXTRAS, it)
            startActivity(intent)
        }

        artistAdapter.setOnItemClickListener {
            val intent = Intent(this, ArtistActivity::class.java)
            intent.putExtra(ArtistActivity.ARTIST_ID_EXTRAS, it)
            startActivity(intent)
        }

        searchField.addTextChangedListener(textWatceh)
    }

    private fun showAlbumsResult(show: Boolean) {
        searchAlbums.visibility = viewVisibilityState(show)
        searchAlbumsList.visibility = viewVisibilityState(show)
        searchProgress.visibility = viewVisibilityState(!show)
    }

    private fun showArtistsResult(show: Boolean) {
        searchArtists.visibility = viewVisibilityState(show)
        searchArtistsList.visibility = viewVisibilityState(show)
        searchProgress.visibility = viewVisibilityState(!show)
    }

    private fun showTracksResult(show: Boolean) {
        searchTracks.visibility = viewVisibilityState(show)
        searchTracksList.visibility = viewVisibilityState(show)
        searchProgress.visibility = viewVisibilityState(!show)
    }

    private fun viewVisibilityState(show: Boolean): Int =
        if (show)
            View.VISIBLE
        else
            View.GONE

    override fun startActivity(intent: Intent?) {
        if (viewModel.isPlaying()) {
            viewModel.stopMediaPlayer()
        }
        super.startActivity(intent)
    }

}