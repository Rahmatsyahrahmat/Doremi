package rahmatsyah.doremi.domain.usecase.search

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Artist
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.repository.AlbumRepository
import rahmatsyah.doremi.domain.repository.ArtistRepository
import rahmatsyah.doremi.domain.repository.TrackRepository

class SearchUseCaseImpl(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository,
    private val trackRepository: TrackRepository
):SearchUseCase {
    override fun searchAlbums(query: String): Flow<Result<List<Album>>> =
        albumRepository.searchAlbums(query)

    override fun searchArtists(query: String): Flow<Result<List<Artist>>> =
        artistRepository.searchArtists(query)

    override fun searchTracks(query: String): Flow<Result<List<Track>>> =
        trackRepository.searchTracks(query)

    override suspend fun addTrackToFavorite(track: Track) {
        trackRepository.addToFavorite(track)
    }
}