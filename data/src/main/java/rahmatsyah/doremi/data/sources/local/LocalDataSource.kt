package rahmatsyah.doremi.data.sources.local

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.data.sources.local.album.AlbumDao
import rahmatsyah.doremi.data.sources.local.album.AlbumEntity
import rahmatsyah.doremi.data.sources.local.artist.ArtistDao
import rahmatsyah.doremi.data.sources.local.artist.ArtistEntity
import rahmatsyah.doremi.data.sources.local.relation.AlbumAndArtist
import rahmatsyah.doremi.data.sources.local.relation.TrackAndAlbumAndArtist
import rahmatsyah.doremi.data.sources.local.track.TrackDao
import rahmatsyah.doremi.data.sources.local.track.TrackEntity


class LocalDataSource constructor(
    private val albumDao: AlbumDao,
    private val artistDao: ArtistDao,
    private val trackDao: TrackDao
) {

    fun getTopAlbums(): Flow<List<AlbumAndArtist>> =
        albumDao.selectTop()

    suspend fun insertTopAlbums(albums: List<AlbumEntity>) {
        albumDao.resetTop()
        albumDao.upsertTop(albums)
    }

    suspend fun insertArtists(artists: List<ArtistEntity>) =
        artistDao.upsert(artists)

    suspend fun insertArtist(artist: ArtistEntity) =
        artistDao.upsert(listOf(artist))

    fun getTopArtists(): Flow<List<ArtistEntity>> =
        artistDao.selectTop()

    suspend fun insertTopArtists(artists: List<ArtistEntity>) {
        artistDao.resetTop()
        artistDao.upsertTop(artists)
    }

    fun getTopTracks(): Flow<List<TrackAndAlbumAndArtist>> =
        trackDao.selectTop()

    suspend fun insertTopTracks(tracks: List<TrackEntity>) {
        trackDao.resetTop()
        trackDao.upsertTop(tracks)
    }

    suspend fun insertAlbums(albums: List<AlbumEntity>) {
        albumDao.upsert(albums)
    }

    suspend fun insertAlbum(album: AlbumEntity) {
        albumDao.upsert(listOf(album))
    }

    fun getAlbum(id: Int): Flow<AlbumAndArtist> =
        albumDao.select(id)

    fun getAlbumTracks(id: Int): Flow<List<TrackEntity>> =
        trackDao.selectTrackByAlbum(id)

    suspend fun insertTracks(tracks: List<TrackEntity>) {
        trackDao.upsert(tracks)
    }

    suspend fun addToFavorite(album: AlbumEntity) {
        albumDao.addToFavorite(album.id, !album.isFavorite)
    }

    fun getArtist(id: Int): Flow<ArtistEntity> =
        artistDao.select(id)

    fun getArtistAlbums(id: Int): Flow<List<AlbumAndArtist>> =
        albumDao.selectAlbumByArtist(id)

    suspend fun addToFavorite(artist: ArtistEntity) {
        artistDao.addToFavorite(artist.id, !artist.isFavorite)
    }

    suspend fun addToFavorite(track: TrackEntity) {
        trackDao.addToFavorite(track.id, !track.isFavorite)
    }

    fun getFavoriteAlbums(): Flow<List<AlbumAndArtist>> =
        albumDao.selectFavorite()

    fun getFavoriteArtists(): Flow<List<ArtistEntity>> =
        artistDao.selectFavorite()

    fun getFavoriteTracks(): Flow<List<TrackAndAlbumAndArtist>> =
        trackDao.selectFavorite()

    fun searchAlbums(query: String): Flow<List<AlbumAndArtist>> =
        albumDao.selectByQuery(query)

    fun searchArtists(query: String): Flow<List<ArtistEntity>> =
        artistDao.selectByQuery(query)

    fun searchTracks(query: String): Flow<List<TrackAndAlbumAndArtist>> =
        trackDao.selectByQuery(query)

}