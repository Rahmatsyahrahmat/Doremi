package rahmatsyah.doremi.data.sources.remote.network

import rahmatsyah.doremi.data.sources.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("chart/0/albums")
    suspend fun getTopAlbums():ListResponse<AlbumResponse>

    @GET("chart/0/artists")
    suspend fun getTopArtists():ListResponse<ArtistResponse>

    @GET("chart/0/tracks")
    suspend fun getTopTracks():ListResponse<TrackResponse>

    @GET("album/{id}")
    suspend fun getAlbum(@Path("id") id:Int):AlbumResponse

    @GET("album/{id}/tracks")
    suspend fun getAlbumTracks(@Path("id") id:Int):ListResponse<TrackResponse>

    @GET("artist/{id}")
    suspend fun getArtist(@Path("id") id:Int):ArtistResponse

    @GET("artist/{id}/albums")
    suspend fun getArtistAlbums(@Path("id") id:Int):ListResponse<AlbumResponse>

    @GET("search/album")
    suspend fun searchAlbums(@Query("q") query:String):ListResponse<AlbumResponse>

    @GET("search/artist")
    suspend fun searchArtists(@Query("q") query:String):ListResponse<ArtistResponse>

    @GET("search/track")
    suspend fun searchTracks(@Query("q") query:String):ListResponse<TrackResponse>
}