package rahmatsyah.doremi.data.sources.remote.response

import com.google.gson.annotations.SerializedName

data class ArtistResponse(
    var id: Int,
    val name: String,
    @SerializedName("picture_big", alternate = ["picture_medium", "picture_small", "picture"])
    val picture: String?
)