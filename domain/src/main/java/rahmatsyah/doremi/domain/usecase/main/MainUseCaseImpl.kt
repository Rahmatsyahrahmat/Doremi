package rahmatsyah.doremi.domain.usecase.main

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.repository.AlbumRepository
import rahmatsyah.doremi.domain.repository.ArtistRepository
import rahmatsyah.doremi.domain.repository.TrackRepository

class MainUseCaseImpl(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository,
    private val trackRepository: TrackRepository
):MainUseCase {
    override fun getTopAlbums(): Flow<Result<List<Album>>> =
        albumRepository.getTopAlbums()

    override fun getTopArtists(): Flow<Result<List<Artist>>> =
        artistRepository.getTopArtists()

    override fun getTopTracks(): Flow<Result<List<Track>>>  =
        trackRepository.getTopTracks()

    override suspend fun addTrackToFavorite(track: Track) {
        trackRepository.addToFavorite(track)
    }
}