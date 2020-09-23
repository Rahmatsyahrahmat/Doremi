package rahmatsyah.doremi.domain.entity

data class Artist(
    val id: Int,
    val name: String,
    val picture: String?,
    var isFavorite: Boolean
)