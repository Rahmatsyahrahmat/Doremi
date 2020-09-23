package rahmatsyah.doremi.search.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rahmatsyah.doremi.search.ui.SearchViewModel

val searchViewModelModul = module {
    viewModel {
        SearchViewModel(get())
    }
}