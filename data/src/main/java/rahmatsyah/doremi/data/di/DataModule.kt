package rahmatsyah.doremi.data.di

import androidx.room.Room
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import rahmatsyah.doremi.data.repository.AlbumRepositoryImpl
import rahmatsyah.doremi.data.repository.ArtistRepositoryImpl
import rahmatsyah.doremi.data.repository.TrackRepositoryImpl
import rahmatsyah.doremi.data.sources.local.AppDatabase
import rahmatsyah.doremi.data.sources.local.LocalDataSource
import rahmatsyah.doremi.data.sources.remote.RemoteDataSource
import rahmatsyah.doremi.data.sources.remote.network.ApiService
import rahmatsyah.doremi.domain.repository.AlbumRepository
import rahmatsyah.doremi.domain.repository.ArtistRepository
import rahmatsyah.doremi.domain.repository.TrackRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<AppDatabase>().albumDao()
    }
    factory {
        get<AppDatabase>().artistDao()
    }
    factory {
        get<AppDatabase>().trackDao()
    }

    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("Doremi".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "Doremi.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.deezer.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/HaNWSc4m0guUgulasK6Tpw2tjjbdCxAHWTHdafYwRCg=")
            .add(hostname, "sha256/YLh1dUR9y6Kja30RrAn7JKnbQG/uEtLMkBgFF2Fuihg=")
            .add(hostname, "sha256/Vjs8r4z+80wjNcr1YKepWQboSIRi63WsWXhIMN+eWys=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.deezer.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single {
        LocalDataSource(get(),get(),get())
    }
    single {
        RemoteDataSource(get())
    }
    single<AlbumRepository> {
        AlbumRepositoryImpl(get(),get())
    }
    single<ArtistRepository> {
        ArtistRepositoryImpl(get(),get())
    }
    single<TrackRepository> {
        TrackRepositoryImpl(get(),get())
    }
}