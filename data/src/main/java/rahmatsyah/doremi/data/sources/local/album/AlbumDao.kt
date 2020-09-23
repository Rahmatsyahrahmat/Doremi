package rahmatsyah.doremi.data.sources.local.album


import androidx.room.*
import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.data.sources.local.relation.AlbumAndArtist

@Dao
interface AlbumDao {

    @Transaction
    @Query("SELECT * FROM AlbumEntity WHERE id = :id")
    fun select(id: Int): Flow<AlbumAndArtist>

    @Transaction
    @Query("SELECT * FROM AlbumEntity WHERE isTop = 1")
    fun selectTop(): Flow<List<AlbumAndArtist>>

    @Transaction
    @Query("SELECT * FROM AlbumEntity WHERE title LIKE '%'||:query||'%'")
    fun selectByQuery(query: String): Flow<List<AlbumAndArtist>>

    @Transaction
    @Query("SELECT * FROM AlbumEntity WHERE isFavorite = 1")
    fun selectFavorite(): Flow<List<AlbumAndArtist>>

    @Transaction
    @Query("SELECT * FROM AlbumEntity WHERE artistId = :artistId")
    fun selectAlbumByArtist(artistId: Int): Flow<List<AlbumAndArtist>>

    @Query("UPDATE AlbumEntity SET isTop = 0")
    suspend fun resetTop()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(albums: List<AlbumEntity>): List<Long>

    @Update
    suspend fun update(album: AlbumEntity)

    @Query("SELECT isTop FROM AlbumEntity WHERE id = :id")
    fun isTop(id: Int): Boolean

    @Query("SELECT isFavorite FROM AlbumEntity WHERE id = :id")
    fun isFavorite(id: Int): Boolean

    @Query("UPDATE AlbumEntity SET isFavorite = :newState WHERE id = :id")
    suspend fun addToFavorite(id: Int, newState: Boolean)

    @Transaction
    suspend fun upsert(albums: List<AlbumEntity>) {
        val insertResult = insert(albums)
        insertResult.forEachIndexed { index, l ->
            if (l == (-1).toLong()) {
                val album = albums[index]
                album.isTop = isTop(album.id)
                album.isFavorite = isFavorite(album.id)
                update(album)
            }
        }
    }

    @Transaction
    suspend fun upsertTop(albums: List<AlbumEntity>) {
        val insertResult = insert(albums)
        insertResult.forEachIndexed { index, l ->
            if (l == (-1).toLong()) {
                val album = albums[index]
                album.isFavorite = isFavorite(album.id)
                update(album)
            }
        }
    }

}