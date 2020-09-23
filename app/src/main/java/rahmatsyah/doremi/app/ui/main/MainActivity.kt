package rahmatsyah.doremi.app.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import rahmatsyah.doremi.app.R
import rahmatsyah.doremi.app.ui.album.AlbumActivity
import rahmatsyah.doremi.app.ui.artist.ArtistActivity
import rahmatsyah.doremi.app.ui.main.adapter.AlbumAdapter
import rahmatsyah.doremi.app.ui.main.adapter.ArtistAdapter
import rahmatsyah.doremi.app.ui.main.adapter.TrackAdapter
import rahmatsyah.doremi.domain.common.Result

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel: MainViewModel by viewModel()

    private val albumAdapter: AlbumAdapter by lazy {
        AlbumAdapter(this)
    }
    private val artistAdapter: ArtistAdapter by lazy {
        ArtistAdapter(this)
    }
    private val trackAdapter: TrackAdapter by lazy {
        TrackAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpView()

        viewModel.topAlbums.observe(this, { result ->
            when (result) {
                is Result.Loading -> {
                    if (result.data.isNullOrEmpty()) {
                        albumsProgress.visibility = View.VISIBLE
                    } else {
                        result.data?.let { items ->
                            albumAdapter.setAlbums(items)
                        }
                    }
                }
                is Result.Success -> {
                    albumsProgress.visibility = View.INVISIBLE
                    result.data?.let { items ->
                        albumAdapter.setAlbums(items)
                    }
                }
                is Result.Error -> {
                    albumsProgress.visibility = View.INVISIBLE
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.topArtists.observe(this, { result ->
            when (result) {
                is Result.Loading -> {
                    if (result.data.isNullOrEmpty()) {
                        artistsProgress.visibility = View.VISIBLE
                    } else {
                        result.data?.let { items ->
                            artistAdapter.setArtists(items)
                        }
                    }
                }
                is Result.Success -> {
                    artistsProgress.visibility = View.INVISIBLE
                    result.data?.let { items ->
                        artistAdapter.setArtists(items)
                    }
                }
                is Result.Error -> {
                    artistsProgress.visibility = View.INVISIBLE
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.topTracks.observe(this, { result ->
            when (result) {
                is Result.Loading -> {
                    if (result.data.isNullOrEmpty()) {
                        tracksProgress.visibility = View.VISIBLE
                    } else {
                        result.data?.let { items ->
                            trackAdapter.setTracks(items)
                        }
                    }
                }
                is Result.Success -> {
                    tracksProgress.visibility = View.GONE
                    result.data?.let { items ->
                        trackAdapter.setTracks(items)
                        viewModel.playedTrackPosition.observe(this, {
                            trackAdapter.setPlayingOn(it)
                        })
                    }
                }
                is Result.Error -> {
                    tracksProgress.visibility = View.GONE
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })

    }

    private fun setUpView() {
        mainTopAlbumsList.adapter = albumAdapter
        mainTopArtistsList.adapter = artistAdapter
        mainTopTracksList.adapter = trackAdapter

        mainMenu.setOnClickListener {
            mainDrawerLayout.openDrawer(mainNavView)
        }

        mainNavView.setNavigationItemSelectedListener(this)

        mainSearchField.setOnClickListener {
            val uri = Uri.parse("doremiapp://search")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
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
    }

    override fun startActivity(intent: Intent) {
        if (viewModel.isPlaying()) {
            viewModel.stopMediaPlayer()
        }
        super.startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuHome -> {
                mainDrawerLayout.closeDrawer(mainNavView)
            }
            R.id.menuFavoriteAlbum -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("doremiapp://favorite_album")))
            }
            R.id.menuFavoriteArtist -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("doremiapp://favorite_artist")))
            }
            R.id.menuFavoriteTrack -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("doremiapp://favorite_track")))
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(mainNavView)) {
            mainDrawerLayout.closeDrawer(mainNavView)
        } else {
            super.onBackPressed()
        }
    }

}