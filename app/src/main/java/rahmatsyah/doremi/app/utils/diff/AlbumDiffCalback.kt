package rahmatsyah.doremi.app.utils.diff

import androidx.recyclerview.widget.DiffUtil
import rahmatsyah.doremi.domain.entity.Album

class AlbumDiffCalback (private val oldItem:List<Album>,private val newItem:List<Album>):DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItem[oldItemPosition].id == newItem[newItemPosition].id

    override fun getOldListSize(): Int =
        oldItem.size

    override fun getNewListSize(): Int =
        newItem.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItem[oldItemPosition]
        val newItem = newItem[newItemPosition]
        return oldItem.title == newItem.title && oldItem.cover == newItem.cover  && oldItem.releaseDate == newItem.releaseDate
    }

}