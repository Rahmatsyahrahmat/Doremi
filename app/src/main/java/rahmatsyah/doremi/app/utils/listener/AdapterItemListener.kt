package rahmatsyah.doremi.app.utils.listener

import rahmatsyah.doremi.domain.entity.Track

interface AdapterItemListener {
    interface OnItemClickListener{
        fun onItemClick(itemId:Int)
    }
}
interface AdapterTrackItemListener{
    interface OnItemPlayListener{
        fun onItemPlay(track:Track,position:Int)
    }
    interface OnItemAddToFavoriteListener{
        fun onItemAddToFavorite(track: Track)
    }
    interface OnItemListenMoreClickListener{
        fun onItemListenMoreClick(link:String)
    }
}