package rahmatsyah.doremi.data.sources.local.track

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import rahmatsyah.doremi.data.sources.local.artist.ArtistEntity
import rahmatsyah.doremi.data.sources.local.relation.AlbumAndArtist
import rahmatsyah.doremi.data.sources.local.relation.TrackAndAlbumAndArtist

@Dao
interface TrackDao{

    @Transaction
    @Query("SELECT * FROM TrackEntity WHERE isTop = 1")
    fun selectTop():Flow<List<TrackAndAlbumAndArtist>>

    @Transaction
    @Query("SELECT * FROM TrackEntity WHERE isFavorite = 1")
    fun selectFavorite():Flow<List<TrackAndAlbumAndArtist>>

    @Transaction
    @Query("SELECT * FROM TrackEntity WHERE title LIKE '%'||:query||'%'")
    fun selectByQuery(query:String):Flow<List<TrackAndAlbumAndArtist>>

    @Query("UPDATE TrackEntity SET isTop = 0")
    suspend fun resetTop()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tracks:List<TrackEntity>):List<Long>

    @Update
    suspend fun update(track: TrackEntity)

    @Query("SELECT isTop FROM TrackEntity WHERE id = :id")
    fun isTop(id:Int):Boolean

    @Query("UPDATE TrackEntity SET isFavorite = :newState WHERE id = :id")
    suspend fun addToFavorite(id: Int,newState:Boolean)

    @Query("SELECT isFavorite FROM TrackEntity WHERE id = :id")
    fun isFavorite(id:Int):Boolean

    @Query("SELECT * FROM TrackEntity WHERE albumId = :id")
    fun selectTrackByAlbum(id:Int):Flow<List<TrackEntity>>

    @Transaction
    suspend fun upsert(tracks:List<TrackEntity>){
        val insertResult = insert(tracks)
        insertResult.forEachIndexed { index, l ->
            if (l == (-1).toLong()){
                val track = tracks[index]
                track.isTop = isTop(track.id)
                track.isFavorite = isFavorite(track.id)
                update(track)
            }
        }
    }

    @Transaction
    suspend fun upsertTop(tracks:List<TrackEntity>){
        val insertResult = insert(tracks)
        insertResult.forEachIndexed { index, l ->
            if (l == (-1).toLong()){
                val track = tracks[index]
                track.isFavorite = isFavorite(track.id)
                update(track)
            }
        }
    }

}