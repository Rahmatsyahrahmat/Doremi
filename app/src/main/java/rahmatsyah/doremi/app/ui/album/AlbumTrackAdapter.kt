package rahmatsyah.doremi.app.ui.album

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_item_album_track.view.*
import rahmatsyah.doremi.app.R
import rahmatsyah.doremi.app.utils.diff.TrackDiffCallback
import rahmatsyah.doremi.app.utils.listener.AdapterTrackItemListener
import rahmatsyah.doremi.domain.entity.Track

class AlbumTrackAdapter(private val context: Context) :
    RecyclerView.Adapter<AlbumTrackAdapter.ViewHolder>() {

    private val tracks: ArrayList<Track> = ArrayList()
    private var playingOn = -1

    private var onItemPlay: AdapterTrackItemListener.OnItemPlayListener? = null
    private var onItemAddToFavorite: AdapterTrackItemListener.OnItemAddToFavoriteListener? = null
    private var onItemMoreClickListener: AdapterTrackItemListener.OnItemListenMoreClickListener? =
        null

    fun setTracks(tracks: List<Track>) {
        val diffCalback = TrackDiffCallback(
            this.tracks,
            tracks
        )
        val diffResult = DiffUtil.calculateDiff(diffCalback)
        this.tracks.clear()
        this.tracks.addAll(tracks)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemPlayListener(play: (track: Track, position: Int) -> Unit) {
        onItemPlay = object : AdapterTrackItemListener.OnItemPlayListener {
            override fun onItemPlay(track: Track, position: Int) {
                play(track, position)
            }

        }
    }

    fun setOnItemAddToFavoriteListener(item: (track: Track) -> Unit) {
        onItemAddToFavorite = object : AdapterTrackItemListener.OnItemAddToFavoriteListener {
            override fun onItemAddToFavorite(track: Track) {
                item(track)
            }

        }
    }

    fun setOnItemListenMoreClickListener(link: (link: String) -> Unit) {
        onItemMoreClickListener = object : AdapterTrackItemListener.OnItemListenMoreClickListener {
            override fun onItemListenMoreClick(link: String) {
                link(link)
            }

        }
    }

    fun setPlayingOn(position: Int) {
        if (position < 0) {
            if (playingOn >= 0) {
                stop(playingOn)
                playingOn = position
            }
        } else {
            play(position)
        }
    }

    fun play(position: Int) {
        if (position < 0) {
            return
        }
        if (playingOn >= 0) {
            tracks[playingOn].isPlaying = false
            notifyItemChanged(playingOn)
        }
        playingOn = position
        tracks[position].isPlaying = true
        notifyItemChanged(position)
    }

    fun stop(position: Int) {
        playingOn = -1
        tracks[position].isPlaying = false
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.view_item_album_track, parent, false)
        )

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tracks[position])

        holder.itemView.trackPlayer.setOnClickListener {
            onItemPlay?.onItemPlay(tracks[position], position)
        }
        holder.itemView.trackFavorite.setOnClickListener {
            onItemAddToFavorite?.onItemAddToFavorite(tracks[position])
        }
        holder.itemView.trackMore.setOnClickListener {
            tracks[position].link?.let { it1 -> onItemMoreClickListener?.onItemListenMoreClick(it1) }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(track: Track) {
            itemView.trackTitle.text = track.title
            if (track.isPlaying) {
                Glide.with(itemView.context).load(R.drawable.ic_pause).into(itemView.trackPlayer)
            } else {
                Glide.with(itemView.context).load(R.drawable.ic_play).into(itemView.trackPlayer)
            }
            if (track.isFavorite) {
                Glide.with(itemView.context).load(R.drawable.ic_favorite_red)
                    .into(itemView.trackFavorite)
            } else {
                Glide.with(itemView.context).load(R.drawable.ic_favorite_border_black)
                    .into(itemView.trackFavorite)
            }
        }
    }

}