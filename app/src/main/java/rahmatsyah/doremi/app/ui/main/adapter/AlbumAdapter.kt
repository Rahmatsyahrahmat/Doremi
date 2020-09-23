package rahmatsyah.doremi.app.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_item_album.view.*
import rahmatsyah.doremi.app.R
import rahmatsyah.doremi.app.utils.listener.AdapterItemListener
import rahmatsyah.doremi.app.utils.diff.AlbumDiffCalback
import rahmatsyah.doremi.domain.entity.Album

class AlbumAdapter (private val context: Context):RecyclerView.Adapter<AlbumAdapter.ViewHolder>(){

    private val albums:ArrayList<Album> = arrayListOf()
    private var onItemClickListener: AdapterItemListener.OnItemClickListener? = null

    fun setAlbums(albums:List<Album>){
        val diffCallback = AlbumDiffCalback(
            this.albums,
            albums
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.albums.clear()
        this.albums.addAll(albums)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(click:(id:Int)->Unit){
        onItemClickListener = object : AdapterItemListener.OnItemClickListener {
            override fun onItemClick(itemId: Int) {
                click(itemId)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_item_album,parent,false))

    override fun getItemCount(): Int  = albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(albums[position].id)
        }
    }

    inner class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        fun bind(album:Album){
            Glide.with(itemView.context).load(album.cover).into(itemView.albumsPicture)
            itemView.albumsTitle.text = album.title
            itemView.albumsArtistName.text = context.resources.getString(R.string.artist_by,album.artist?.name)
        }
    }

}