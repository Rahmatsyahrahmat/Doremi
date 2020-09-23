package rahmatsyah.doremi.app.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rahmatsyah.doremi.app.ui.album.AlbumViewModel
import rahmatsyah.doremi.app.ui.artist.ArtistViewModel
import rahmatsyah.doremi.app.ui.main.MainViewModel
import rahmatsyah.doremi.app.ui.splash.SplashViewModel
import rahmatsyah.doremi.domain.usecase.album.AlbumUseCase
import rahmatsyah.doremi.domain.usecase.album.AlbumUseCaseImpl
import rahmatsyah.doremi.domain.usecase.atist.ArtistUseCase
import rahmatsyah.doremi.domain.usecase.atist.ArtistUseCaseImpl
import rahmatsyah.doremi.domain.usecase.favorite.album.FavoriteAlbumUseCase
import rahmatsyah.doremi.domain.usecase.favorite.album.FavoriteAlbumUseCaseImpl
import rahmatsyah.doremi.domain.usecase.favorite.artist.FavoriteArtistUseCase
import rahmatsyah.doremi.domain.usecase.favorite.artist.FavoriteArtistUseCaseImpl
import rahmatsyah.doremi.domain.usecase.favorite.track.FavoriteTrackUseCase
import rahmatsyah.doremi.domain.usecase.favorite.track.FavoriteTrackUseCaseImpl
import rahmatsyah.doremi.domain.usecase.main.MainUseCase
import rahmatsyah.doremi.domain.usecase.main.MainUseCaseImpl
import rahmatsyah.doremi.domain.usecase.search.SearchUseCase
import rahmatsyah.doremi.domain.usecase.search.SearchUseCaseImpl

val appModule = module {
    factory<MainUseCase> {
        MainUseCaseImpl(get(),get(),get())
    }
    factory<AlbumUseCase> {
        AlbumUseCaseImpl(get(),get())
    }
    factory<ArtistUseCase> {
        ArtistUseCaseImpl(get())
    }
    factory<FavoriteAlbumUseCase> {
        FavoriteAlbumUseCaseImpl(get())
    }
    factory<FavoriteArtistUseCase> {
        FavoriteArtistUseCaseImpl(get())
    }
    factory<FavoriteTrackUseCase> {
        FavoriteTrackUseCaseImpl(get())
    }
    factory<SearchUseCase> {
        SearchUseCaseImpl(get(),get(),get())
    }
}

val appViewModelModul = module {
    viewModel {
        SplashViewModel()
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        AlbumViewModel(get())
    }
    viewModel {
        ArtistViewModel(get())
    }
}