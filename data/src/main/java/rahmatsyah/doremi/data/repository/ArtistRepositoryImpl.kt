package rahmatsyah.doremi.data.repository

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.data.mapper.*
import rahmatsyah.doremi.data.sources.local.LocalDataSource
import rahmatsyah.doremi.data.sources.remote.RemoteDataSource
import rahmatsyah.doremi.data.sources.remote.network.ApiResponse
import rahmatsyah.doremi.data.sources.remote.response.AlbumResponse
import rahmatsyah.doremi.data.sources.remote.response.ArtistResponse
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.repository.ArtistRepository

class ArtistRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ArtistRepository {
    override fun getArtist(id: Int): Flow<Result<Artist>> =
        object : NetworkBoundResource<Artist, ArtistResponse>() {
            override fun loadFromDB(): Flow<Artist> =
                localDataSource.getArtist(id).toArtist()

            override fun shouldFetch(data: Artist?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<ArtistResponse>> =
                remoteDataSource.getArtist(id)

            override suspend fun saveCallResult(data: ArtistResponse) {
                localDataSource.insertArtist(data.toArtistEntity())
            }

        }.asFlow()

    override fun getArtistAlbums(id: Int): Flow<Result<List<Album>>> =
        object : NetworkBoundResource<List<Album>, List<AlbumResponse>>() {
            override fun loadFromDB(): Flow<List<Album>> =
                localDataSource.getArtistAlbums(id).toAlbumList()

            override fun shouldFetch(data: List<Album>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<AlbumResponse>>> =
                remoteDataSource.getArtistAlbum(id)

            override suspend fun saveCallResult(data: List<AlbumResponse>) {
                localDataSource.insertAlbums(data.toAlbumEntitiesWithArtistId(id))
            }

        }.asFlow()

    override fun getTopArtists(): Flow<Result<List<Artist>>> =
        object : NetworkBoundResource<List<Artist>, List<ArtistResponse>>() {
            override fun loadFromDB(): Flow<List<Artist>> =
                localDataSource.getTopArtists().toArtistList()

            override fun shouldFetch(data: List<Artist>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<ArtistResponse>>> =
                remoteDataSource.getTopArtists()

            override suspend fun saveCallResult(data: List<ArtistResponse>) {
                localDataSource.insertTopArtists(data.toArtistEntities(true))
            }

        }.asFlow()

    override fun getFavoriteArtists(): Flow<List<Artist>> =
        localDataSource.getFavoriteArtists().toArtistList()

    override fun searchArtists(query: String): Flow<Result<List<Artist>>> =
        object : NetworkBoundResource<List<Artist>, List<ArtistResponse>>() {
            override fun loadFromDB(): Flow<List<Artist>> =
                localDataSource.searchArtists(query).toArtistList()

            override fun shouldFetch(data: List<Artist>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<ArtistResponse>>> =
                remoteDataSource.searchArtists(query)

            override suspend fun saveCallResult(data: List<ArtistResponse>) {
                localDataSource.insertArtists(data.toArtistEntities())
            }

        }.asFlow()


    override suspend fun addToFavorite(artist: Artist) {
        localDataSource.addToFavorite(artist.toArtistEntity())
    }
}