package rahmatsyah.doremi.domain.entity

data class Album(
    val id: Int,
    val title: String,
    val releaseDate: String?,
    val cover: String?,
    var artist: Artist? = null,
    var isFavorite: Boolean
)