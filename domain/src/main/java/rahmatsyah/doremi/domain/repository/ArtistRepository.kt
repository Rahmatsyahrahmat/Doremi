package rahmatsyah.doremi.domain.repository

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist

interface ArtistRepository {
    fun getArtist(id:Int):Flow<Result<Artist>>
    fun getArtistAlbums(id:Int):Flow<Result<List<Album>>>
    fun getTopArtists():Flow<Result<List<Artist>>>
    fun getFavoriteArtists():Flow<List<Artist>>
    fun searchArtists(query:String):Flow<Result<List<Artist>>>
    suspend fun addToFavorite(artist: Artist)
}