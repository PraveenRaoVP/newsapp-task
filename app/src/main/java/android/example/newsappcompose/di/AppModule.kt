package android.example.newsappcompose.di

import android.app.Application
import android.example.newsappcompose.data.local.NewsDao
import android.example.newsappcompose.data.local.NewsDatabase
import android.example.newsappcompose.data.local.NewsTypeConverter
import android.example.newsappcompose.data.manager.LocalUserManagerImpl
import android.example.newsappcompose.data.remote.NewsApi
import android.example.newsappcompose.data.repository.NewsRepositoryImpl
import android.example.newsappcompose.domain.manager.LocalUserManager
import android.example.newsappcompose.domain.repository.NewsRepository
import android.example.newsappcompose.domain.usecases.appentry.AppEntryUsecases
import android.example.newsappcompose.domain.usecases.appentry.ReadAppEntry
import android.example.newsappcompose.domain.usecases.appentry.SaveAppEntry
import android.example.newsappcompose.domain.usecases.news.AddCustomArticle
import android.example.newsappcompose.domain.usecases.news.DeleteArticle
import android.example.newsappcompose.domain.usecases.news.GetNews
import android.example.newsappcompose.domain.usecases.news.NewsUseCases
import android.example.newsappcompose.domain.usecases.news.GetArticles
import android.example.newsappcompose.domain.usecases.news.GetCustomArticle
import android.example.newsappcompose.domain.usecases.news.SearchNews
import android.example.newsappcompose.domain.usecases.news.SelectArticle
import android.example.newsappcompose.domain.usecases.news.UpsertArticle
import android.example.newsappcompose.util.Constants
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUsecases(
        localUserManager: LocalUserManager
    ) = AppEntryUsecases(
        saveAppEntry = SaveAppEntry(localUserManager),
        readAppEntry = ReadAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository = newsRepository),
            searchNews = SearchNews(newsRepository = newsRepository),
            upsertNews = UpsertArticle(newsRepository = newsRepository),
            deleteNews = DeleteArticle(newsRepository = newsRepository),
            searchArticle = GetArticles(newsRepository = newsRepository),
            selectArticle = SelectArticle(newsRepository = newsRepository),
            addCustomArticle = AddCustomArticle(newsRepository = newsRepository),
            getCustomArticle = GetCustomArticle(newsRepository = newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository {
        return NewsRepositoryImpl(
            newsApi = newsApi,
            newsDao = newsDao
        )
    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao()
}

/*
    Notes:-
- AppModule is a Dagger module that provides dependencies for the app.
- The @InstallIn annotation tells Dagger to install the module in the SingletonComponent.
- The @Provides annotation tells Dagger that the method provides a dependency.
- The provideLocalUserManager method provides an instance of the LocalUserManager interface.
- The provideAppEntryUsecases method provides an instance of the AppEntryUsecases class.
- The provideNewsUseCases method provides an instance of the NewsUseCases class.
- The provideNewsRepository method provides an instance of the NewsRepository interface.
- The provideNewsApi method provides an instance of the NewsApi interface.
- The provideNewsApi method creates a Retrofit instance with the base URL and GsonConverterFactory.
Why do we have to create a method with the Provides instead of creating an instance directly?
- The @Provides annotation tells Dagger that the method provides a dependency. By using this annotation,
Dagger knows that the method provides a dependency that can be injected into other classes.
What are the Advantages of Dependency Injection?
- Dependency injection makes it easier to manage dependencies between classes, improves code reusability, and makes testing easier.
What is the purpose of the AppModule class?
- The AppModule class provides dependencies for the app using Dagger's @Provides annotation.
How does Provides know where to inject ?
- The @Provides annotation tells Dagger that the method provides a dependency that can be injected into other classes.
     */