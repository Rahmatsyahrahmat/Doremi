package rahmatsyah.doremi.app.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_item_artist.view.*
import rahmatsyah.doremi.app.R
import rahmatsyah.doremi.app.utils.diff.ArtistDiffCalback
import rahmatsyah.doremi.app.utils.listener.AdapterItemListener
import rahmatsyah.doremi.domain.entity.Artist

class ArtistAdapter(private val context: Context) :
    RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    private val artists: ArrayList<Artist> = arrayListOf()
    private var onItemClickListener: AdapterItemListener.OnItemClickListener? = null

    fun setOnItemClickListener(click: (id: Int) -> Unit) {
        onItemClickListener = object : AdapterItemListener.OnItemClickListener {
            override fun onItemClick(itemId: Int) {
                click(itemId)
            }

        }
    }

    fun setArtists(artists: List<Artist>) {
        val diffCallback = ArtistDiffCalback(
            this.artists,
            artists
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.artists.clear()
        this.artists.addAll(artists)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_item_artist, parent, false))

    override fun getItemCount(): Int = artists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(artists[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(artists[position].id)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(artist: Artist) {
            Glide.with(itemView.context).load(artist.picture).into(itemView.artistsPicture)
            itemView.artistsName.text = artist.name
        }
    }

}