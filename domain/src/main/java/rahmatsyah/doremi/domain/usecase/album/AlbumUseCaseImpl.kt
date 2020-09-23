package rahmatsyah.doremi.domain.usecase.album

import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.domain.common.Result
import rahmatsyah.doremi.domain.entity.Album
import rahmatsyah.doremi.domain.entity.Track
import rahmatsyah.doremi.domain.repository.AlbumRepository
import rahmatsyah.doremi.domain.repository.TrackRepository

class AlbumUseCaseImpl(private val albumRepository: AlbumRepository,private val trackRepository: TrackRepository):AlbumUseCase {
    override fun getAlbum(id: Int): Flow<Result<Album>> =
        albumRepository.getAlbum(id)

    override fun getAlbumTracks(id: Int): Flow<Result<List<Track>>> =
        albumRepository.getAlbumTracks(id)

    override suspend fun addToFavorite(album: Album) {
        albumRepository.addToFavorite(album)
    }

    override suspend fun addTrackToFavorite(track: Track) {
        trackRepository.addToFavorite(track)
    }
}