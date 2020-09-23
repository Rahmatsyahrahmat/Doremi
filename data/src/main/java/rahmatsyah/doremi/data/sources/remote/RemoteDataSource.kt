package rahmatsyah.doremi.data.sources.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rahmatsyah.doremi.data.sources.remote.network.ApiResponse
import rahmatsyah.doremi.data.sources.remote.network.ApiService
import rahmatsyah.doremi.data.sources.remote.response.AlbumResponse
import rahmatsyah.doremi.data.sources.remote.response.ArtistResponse
import rahmatsyah.doremi.data.sources.remote.response.TrackResponse
import java.lang.Exception


class RemoteDataSource constructor(private val apiService: ApiService){

    suspend fun getTopAlbums():Flow<ApiResponse<List<AlbumResponse>>> =
        flow {
            try {
                val response = apiService.getTopAlbums()
                if (response.data.isNullOrEmpty()){
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(response.data))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getTopArtists():Flow<ApiResponse<List<ArtistResponse>>> =
        flow {
            try{
                val response = apiService.getTopArtists()
                if (response.data.isNullOrEmpty()){
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(response.data))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getTopTracks():Flow<ApiResponse<List<TrackResponse>>> =
        flow {
            try {
                val response = apiService.getTopTracks()
                if (response.data.isNullOrEmpty()){
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(response.data))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getAlbum(id:Int):Flow<ApiResponse<AlbumResponse>> =
        flow {
            try {
                val response = apiService.getAlbum(id)
                emit(ApiResponse.Success(response))
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)


    suspend fun getAlbumTracks(id:Int):Flow<ApiResponse<List<TrackResponse>>> =
        flow {
            try {
                val response = apiService.getAlbumTracks(id)
                if (response.data.isNullOrEmpty()){
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(response.data))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getArtist(id:Int):Flow<ApiResponse<ArtistResponse>> =
        flow {
            try {
                val response = apiService.getArtist(id)
                emit(ApiResponse.Success(response))
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getArtistAlbum(id: Int):Flow<ApiResponse<List<AlbumResponse>>> =
        flow {
            try {
                val response = apiService.getArtistAlbums(id)
                if (response.data.isNullOrEmpty()){
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(response.data))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun searchAlbums(query:String):Flow<ApiResponse<List<AlbumResponse>>> =
        flow {
            try {
                val response = apiService.searchAlbums(query)
                if (response.data.isNullOrEmpty()){
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(response.data))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun searchArtists(query:String):Flow<ApiResponse<List<ArtistResponse>>> =
        flow {
            try {
                val response = apiService.searchArtists(query)
                if (response.data.isNullOrEmpty()){
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(response.data))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun searchTracks(query:String):Flow<ApiResponse<List<TrackResponse>>> =
        flow {
            try {
                val response = apiService.searchTracks(query)
                if (response.data.isNullOrEmpty()){
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(response.data))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

}