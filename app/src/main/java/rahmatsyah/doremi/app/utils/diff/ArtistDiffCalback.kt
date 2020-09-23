package rahmatsyah.doremi.app.utils.diff

import androidx.recyclerview.widget.DiffUtil
import rahmatsyah.doremi.domain.entity.Artist


class ArtistDiffCalback(private val oldItem: List<Artist>, private val newItem: List<Artist>) :
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
        return oldItem.name == newItem.name && oldItem.picture == newItem.picture
    }

}