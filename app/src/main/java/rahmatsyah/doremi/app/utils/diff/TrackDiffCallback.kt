package rahmatsyah.doremi.app.utils.diff

import androidx.recyclerview.widget.DiffUtil
import rahmatsyah.doremi.domain.entity.Track


class TrackDiffCallback(private val oldItem: List<Track>, private val newItem: List<Track>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItem[oldItemPosition].id == newItem[newItemPosition].id

    override fun getOldListSize(): Int =
        oldItem.size

    override fun getNewListSize(): Int =
        newItem.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItem[oldItemPosition]
        val newItem = newItem[newItemPosition]
        return oldItem.title == newItem.title && oldItem.link == newItem.link && oldItem.preview == newItem.preview
    }

}