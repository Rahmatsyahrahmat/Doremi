package rahmatsyah.doremi.favorite.ui.artist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_favorite_artist.*
import kotlinx.android.synthetic.main.view_empty.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import rahmatsyah.doremi.app.ui.artist.ArtistActivity
import rahmatsyah.doremi.app.ui.main.adapter.ArtistAdapter
import rahmatsyah.doremi.favorite.R
import rahmatsyah.doremi.favorite.di.favoriteViewModelModule

class FavoriteArtistActivity : AppCompatActivity() {

    private val viewModel: FavoriteArtistViewModel by viewModel()

    private val artistAdapter: ArtistAdapter by lazy {
        ArtistAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_artist)

        loadKoinModules(favoriteViewModelModule)

        favoriteArtists.adapter = artistAdapter

        artistAdapter.setOnItemClickListener {
            val intent = Intent(this, ArtistActivity::class.java)
            intent.putExtra(ArtistActivity.ARTIST_ID_EXTRAS, it)
            startActivity(intent)
        }

        viewModel.getFavoriteArtists().observe(this, {
            if (it.isNullOrEmpty()) {
                emptyList.visibility = View.VISIBLE
                emptyMessage.text = getString(R.string.no_list_of_favorite_artists)
            }
            artistAdapter.setArtists(it)
        })

    }
}