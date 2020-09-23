package rahmatsyah.doremi.app.ui.artist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_artist.*
import org.koin.android.viewmodel.ext.android.viewModel
import rahmatsyah.doremi.app.R
import rahmatsyah.doremi.app.ui.album.AlbumActivity
import rahmatsyah.doremi.app.ui.main.adapter.AlbumAdapter
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Artist

class ArtistActivity : AppCompatActivity() {

    companion object {
        const val ARTIST_ID_EXTRAS = "artist_id_extras"
    }

    private val viewModel:ArtistViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)
        setSupportActionBar(toolbar)

        val artistId = intent.getIntExtra(ARTIST_ID_EXTRAS,0)
        viewModel.setArtistId(artistId)

        val albumAdapter = AlbumAdapter(this)
        artistAlbumList.adapter = albumAdapter

        albumAdapter.setOnItemClickListener {
            val intent = Intent(this,AlbumActivity::class.java)
            intent.putExtra(AlbumActivity.ALBUM_ID_EXTRAS,it)
            startActivity(intent)
        }

        artistFavorite.setOnClickListener {
            viewModel.addToFavorite()
        }

        viewModel.artist.observe(this, Observer {resource->
            when(resource){
                is Result.Loading ->{
                    resource.data?.let {
                        initArtist(it)
                    }
                }
                is Result.Error ->{
                    Toast.makeText(this,resource.message.toString(),Toast.LENGTH_SHORT).show()
                }
                is Result.Success ->{
                    resource.data?.let {
                        initArtist(it)
                    }
                }
            }
        })

        viewModel.albums.observe(this, Observer {resource->
            when(resource){
                is Result.Loading ->{
                    resource.data?.let { albumAdapter.setAlbums(it) }
                }
                is Result.Error ->{
                    Toast.makeText(this,resource.message.toString(),Toast.LENGTH_SHORT).show()
                }
                is Result.Success ->{
                    resource.data?.let { albumAdapter.setAlbums(it) }
                }
            }
        })

    }

    private fun initArtist(artist:Artist){
        Glide.with(this).load(artist.picture).into(artistPicture)
        toolbarLayout.title = artist.name
        artist.isFavorite.let {
            if (it)
                Glide.with(this).load(R.drawable.ic_favorite_red).into(artistFavorite)
            else
                Glide.with(this).load(R.drawable.ic_favorite_border_black).into(artistFavorite)
        }
    }
}