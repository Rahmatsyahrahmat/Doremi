package rahmatsyah.doremi.data.sources.local.artist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Query("SELECT * FROM ArtistEntity WHERE isTop = 1")
    fun selectTop(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM ArtistEntity WHERE isFavorite = 1")
    fun selectFavorite(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM ArtistEntity WHERE name LIKE '%'||:query||'%'")
    fun selectByQuery(query: String): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM ArtistEntity WHERE id = :id")
    fun select(id: Int): Flow<ArtistEntity>

    @Query("UPDATE ArtistEntity SET isTop = 0")
    suspend fun resetTop()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(artists: List<ArtistEntity>): List<Long>

    @Update
    suspend fun update(artist: ArtistEntity)

    @Query("UPDATE ArtistEntity SET isFavorite = :newState WHERE id = :id")
    suspend fun addToFavorite(id: Int, newState: Boolean)

    @Query("SELECT isTop FROM ArtistEntity WHERE id = :id")
    fun isTop(id: Int): Boolean

    @Query("SELECT isFavorite FROM ArtistEntity WHERE id = :id")
    fun isFavorite(id: Int): Boolean

    @Transaction
    suspend fun upsert(artists: List<ArtistEntity>) {
        val insertResult = insert(artists)
        insertResult.forEachIndexed { index, l ->
            if (l == (-1).toLong()) {
                val artist = artists[index]
                artist.isTop = isTop(artist.id)
                artist.isFavorite = isFavorite(artist.id)
                update(artist)
            }
        }
    }

    @Transaction
    suspend fun upsertTop(artists: List<ArtistEntity>) {
        val insertResult = insert(artists)
        insertResult.forEachIndexed { index, l ->
            if (l == (-1).toLong()) {
                val artist = artists[index]
                artist.isFavorite = isFavorite(artist.id)
                update(artist)
            }
        }
    }

}