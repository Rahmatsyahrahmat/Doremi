package rahmatsyah.doremi.data.repository

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.data.mapper.*
import rahmatsyah.doremi.data.sources.local.LocalDataSource
import rahmatsyah.doremi.data.sources.remote.RemoteDataSource
import rahmatsyah.doremi.data.sources.remote.network.ApiResponse
import rahmatsyah.doremi.data.sources.remote.response.AlbumResponse
import rahmatsyah.doremi.data.sources.remote.response.TrackResponse
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.repository.AlbumRepository

class AlbumRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : AlbumRepository {
    override fun getAlbum(id: Int): Flow<Result<Album>> =
        object : NetworkBoundResource<Album, AlbumResponse>() {
            override fun loadFromDB(): Flow<Album> =
                localDataSource.getAlbum(id).toAlbum()

            override fun shouldFetch(data: Album?): Boolean =
                data == null || data.releaseDate.isNullOrBlank()

            override suspend fun createCall(): Flow<ApiResponse<AlbumResponse>> =
                remoteDataSource.getAlbum(id)

            override suspend fun saveCallResult(data: AlbumResponse) {
                localDataSource.insertAlbum(data.toAlbumEntity())
                data.artist?.toArtistEntity()?.let { localDataSource.insertArtist(it) }
            }

        }.asFlow()

    override fun getAlbumTracks(id: Int): Flow<Result<List<Track>>> =
        object : NetworkBoundResource<List<Track>, List<TrackResponse>>() {
            override fun loadFromDB(): Flow<List<Track>> =
                localDataSource.getAlbumTracks(id).toTrackList()

            override fun shouldFetch(data: List<Track>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<TrackResponse>>> =
                remoteDataSource.getAlbumTracks(id)

            override suspend fun saveCallResult(data: List<TrackResponse>) {
                localDataSource.insertTracks(data.toTrackEntitiesWithAlbumId(id))
            }

        }.asFlow()

    override fun getTopAlbums(): Flow<Result<List<Album>>> =
        object : NetworkBoundResource<List<Album>, List<AlbumResponse>>() {
            override fun loadFromDB(): Flow<List<Album>> =
                localDataSource.getTopAlbums().toAlbumList()

            override fun shouldFetch(data: List<Album>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<AlbumResponse>>> =
                remoteDataSource.getTopAlbums()

            override suspend fun saveCallResult(data: List<AlbumResponse>) {
                localDataSource.insertTopAlbums(data.toAlbumEntities(true))
                localDataSource.insertArtists(data.getArtistEntities())
            }

        }.asFlow()

    override fun getFavoriteAlbums(): Flow<List<Album>> =
        localDataSource.getFavoriteAlbums().toAlbumList()

    override fun searchAlbums(query: String): Flow<Result<List<Album>>> =
        object : NetworkBoundResource<List<Album>, List<AlbumResponse>>() {
            override fun loadFromDB(): Flow<List<Album>> =
                localDataSource.searchAlbums(query).toAlbumList()

            override fun shouldFetch(data: List<Album>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<AlbumResponse>>> =
                remoteDataSource.searchAlbums(query)

            override suspend fun saveCallResult(data: List<AlbumResponse>) {
                localDataSource.insertAlbums(data.toAlbumEntities())
                localDataSource.insertArtists(data.getArtistEntities())
            }

        }.asFlow()

    override suspend fun addToFavorite(album: Album) {
        localDataSource.addToFavorite(album.toAlbumEntity())
    }

}