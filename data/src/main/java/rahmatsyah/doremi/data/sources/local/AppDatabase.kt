package rahmatsyah.doremi.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import rahmatsyah.doremi.data.sources.local.album.AlbumDao
import rahmatsyah.doremi.data.sources.local.album.AlbumEntity
import rahmatsyah.doremi.data.sources.local.artist.ArtistDao
import rahmatsyah.doremi.data.sources.local.artist.ArtistEntity
import rahmatsyah.doremi.data.sources.local.track.TrackDao
import rahmatsyah.doremi.data.sources.local.track.TrackEntity

@Database(
    entities = [AlbumEntity::class, ArtistEntity::class, TrackEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun artistDao(): ArtistDao
    abstract fun trackDao(): TrackDao

}