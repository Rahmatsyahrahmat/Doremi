package rahmatsyah.doremi.data.sources.remote.response

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    val id: Int,
    val title: String,
    @SerializedName("release_date")
    val releasedDate: String?,
    @SerializedName("cover_big", alternate = ["cover_medium", "cover_small", "cover"])
    val cover: String?,
    var artist: ArtistResponse?
)