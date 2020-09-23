package rahmatsyah.doremi.favorite.ui.album

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_favorite_album.*
import kotlinx.android.synthetic.main.view_empty.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import rahmatsyah.doremi.app.ui.album.AlbumActivity
import rahmatsyah.doremi.app.ui.main.adapter.AlbumAdapter
import rahmatsyah.doremi.favorite.R
import rahmatsyah.doremi.favorite.di.favoriteViewModelModule

class FavoriteAlbumActivity : AppCompatActivity() {

    private val viewModel:FavoriteAlbumViewModel by viewModel()
    private val albumAdapter:AlbumAdapter by lazy {
        AlbumAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_album)

        loadKoinModules(favoriteViewModelModule)

        favoriteAlbums.adapter = albumAdapter

        albumAdapter.setOnItemClickListener {
            val intent = Intent(this,AlbumActivity::class.java)
            intent.putExtra(AlbumActivity.ALBUM_ID_EXTRAS,it)
            startActivity(intent)
        }

        viewModel.getFavoriteAlbums().observe(this, Observer {
            if (it.isNullOrEmpty()) {
                emptyList.visibility = View.VISIBLE
                emptyMessage.text = getString(R.string.no_list_of_favorite_albums)
            }
            albumAdapter.setAlbums(it)
        })

    }
}