package rahmatsyah.doremi.data.repository

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.data.mapper.*
import rahmatsyah.doremi.data.sources.local.LocalDataSource
import rahmatsyah.doremi.data.sources.remote.RemoteDataSource
import rahmatsyah.doremi.data.sources.remote.network.ApiResponse
import rahmatsyah.doremi.data.sources.remote.response.TrackResponse
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.repository.TrackRepository

class TrackRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : TrackRepository {

    override fun getTopTracks(): Flow<Result<List<Track>>> =
        object : NetworkBoundResource<List<Track>, List<TrackResponse>>() {
            override fun loadFromDB(): Flow<List<Track>> =
                localDataSource.getTopTracks().toTrackList()

            override fun shouldFetch(data: List<Track>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<TrackResponse>>> =
                remoteDataSource.getTopTracks()

            override suspend fun saveCallResult(data: List<TrackResponse>) {
                localDataSource.insertTopTracks(data.toTrackEntities(true))
                localDataSource.insertAlbums(data.getAlbumEntities())
                localDataSource.insertArtists(data.getArtistEntities())
            }

        }.asFlow()

    override fun getFavoriteTracks(): Flow<List<Track>> =
        localDataSource.getFavoriteTracks().toTrackList()

    override fun searchTracks(query: String): Flow<Result<List<Track>>> =
        object : NetworkBoundResource<List<Track>, List<TrackResponse>>() {
            override fun loadFromDB(): Flow<List<Track>> =
                localDataSource.searchTracks(query).toTrackList()

            override fun shouldFetch(data: List<Track>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<TrackResponse>>> =
                remoteDataSource.searchTracks(query)

            override suspend fun saveCallResult(data: List<TrackResponse>) {
                localDataSource.insertTracks(data.toTrackEntities())
                localDataSource.insertAlbums(data.getAlbumEntities())
                localDataSource.insertArtists(data.getArtistEntities())
            }

        }.asFlow()

    override suspend fun addToFavorite(track: Track) {
        localDataSource.addToFavorite(track.toTrackEntity())
    }
}