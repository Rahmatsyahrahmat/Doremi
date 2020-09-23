package rahmatsyah.doremi.favorite.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rahmatsyah.doremi.favorite.ui.album.FavoriteAlbumViewModel
import rahmatsyah.doremi.favorite.ui.artist.FavoriteArtistViewModel
import rahmatsyah.doremi.favorite.ui.track.FavoriteTrackViewModel

val favoriteViewModelModule = module {
    viewModel {
        FavoriteAlbumViewModel(get())
    }
    viewModel {
        FavoriteArtistViewModel(get())
    }
    viewModel {
        FavoriteTrackViewModel(get())
    }
}