package com.gyg.codelab.movies.di

import com.gyg.codelab.movies.data.api.MoviesApiService
import com.gyg.codelab.movies.data.repository.FavoritesRepositoryImpl
import com.gyg.codelab.movies.data.repository.MoviesRepositoryImpl
import com.gyg.codelab.movies.data.repository.SearchRepositoryImpl
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.gyg.codelab.movies.domain.repository.MoviesRepository
import com.gyg.codelab.movies.domain.repository.SearchRepository
import com.gyg.codelab.movies.mvi.starter.favorites.favoritesStarterMviModule
import com.gyg.codelab.movies.mvi.starter.home.homeStarterMviModule
import com.gyg.codelab.movies.mvi.starter.search.searchStarterMviModule
import com.gyg.codelab.movies.mvvm.starter.favorites.FavoritesViewModel
import com.gyg.codelab.movies.mvvm.starter.home.HomeViewModel
import com.gyg.codelab.movies.mvvm.starter.search.SearchViewModel
import com.gyg.codelab.movies.workflow.di.workflowModule
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    // API Key from BuildConfig
    single<String>(qualifier = org.koin.core.qualifier.named("apiKey")) {
        com.harny.droidconkemovieapp.BuildConfig.TMDB_API_KEY
    }

    // OkHttp
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API Service
    single {
        get<Retrofit>().create(MoviesApiService::class.java)
    }

    // Coroutine Dispatcher for IO operations
    single(qualifier = org.koin.core.qualifier.named("ioDispatcher")) {
        Dispatchers.IO
    }

    // Repositories - now separated by responsibility
    single<MoviesRepository> {
        MoviesRepositoryImpl(
            apiService = get(),
            apiKey = get(org.koin.core.qualifier.named("apiKey")),
            ioDispatcher = get(org.koin.core.qualifier.named("ioDispatcher")),
        )
    }

    single<SearchRepository> {
        SearchRepositoryImpl(
            apiService = get(),
            apiKey = get(org.koin.core.qualifier.named("apiKey")),
            ioDispatcher = get(org.koin.core.qualifier.named("ioDispatcher")),
        )
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl()
    }

    // ViewModels - separated by feature, using repositories directly
    // Navigation is now handled in the app layer
    viewModel {
        HomeViewModel(
            moviesRepository = get(),
            favoritesRepository = get(),
        )
    }
    viewModel {
        SearchViewModel(
            searchRepository = get(),
            favoritesRepository = get(),
        )
    }
    viewModel { FavoritesViewModel(favoritesRepository = get()) }

    // Include MVI module
    includes(homeStarterMviModule)
    includes(searchStarterMviModule)
    includes(favoritesStarterMviModule)
    includes(workflowModule)
}
