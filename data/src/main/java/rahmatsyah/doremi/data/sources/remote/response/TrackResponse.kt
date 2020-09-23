package rahmatsyah.doremi.data.sources.remote.response

data class TrackResponse(
    val id: Int,
    val title: String,
    val link: String?,
    val preview: String?,
    val artist: ArtistResponse,
    var album: AlbumResponse?
)