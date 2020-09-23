package rahmatsyah.doremi.data.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rahmatsyah.doremi.data.sources.local.artist.ArtistEntity
import rahmatsyah.doremi.data.sources.remote.response.ArtistResponse
import rahmatsyah.doremi.domain.entity.Artist


fun ArtistEntity.toArtist(): Artist =
    Artist(
        this.id,
        this.name,
        this.picture,
        this.isFavorite
    )

fun ArtistResponse.toArtistEntity(isTop: Boolean = false): ArtistEntity =
    ArtistEntity(
        this.id,
        this.name,
        this.picture,
        isTop = isTop
    )

fun Artist.toArtistEntity(): ArtistEntity =
    ArtistEntity(
        this.id,
        this.name,
        this.picture,
        this.isFavorite
    )

fun Flow<List<ArtistEntity>>.toArtistList(): Flow<List<Artist>> =
    this.map {
        it.toArtistList()
    }

fun Flow<ArtistEntity>.toArtist(): Flow<Artist> =
    this.map {
        it.toArtist()
    }

fun List<ArtistEntity>.toArtistList(): List<Artist> =
    this.map {
        it.toArtist()
    }

fun List<ArtistResponse>.toArtistEntities(isTop: Boolean = false): List<ArtistEntity> =
    this.map {
        it.toArtistEntity(isTop)
    }