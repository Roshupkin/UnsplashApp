package android.com.roshchupkin.unsplashapp.di

import android.com.roshchupkin.unsplashapp.database.dao.RandomImageDao
import android.com.roshchupkin.unsplashapp.database.mapper.ImageCacheMapper
import android.com.roshchupkin.unsplashapp.network.mapper.CollectionNetworkMapper
import android.com.roshchupkin.unsplashapp.network.mapper.ImageNetworkMapper
import android.com.roshchupkin.unsplashapp.network.mapper.SearchImagesNetworkMapper
import android.com.roshchupkin.unsplashapp.network.service.UnsplashAPI
import android.com.roshchupkin.unsplashapp.repository.*
import android.com.roshchupkin.unsplashapp.repository.collection.CollectionRepository
import android.com.roshchupkin.unsplashapp.repository.collection.image.ImageByCollectionRepository
import android.com.roshchupkin.unsplashapp.repository.random.RandomImageRepository
import android.com.roshchupkin.unsplashapp.repository.search.SearchImageRepository
import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRundomPhotoRepository(
        unsplashAPI: UnsplashAPI,
        randomImageDao: RandomImageDao,
        imageCacheMapper: ImageCacheMapper,
        imageNetworkMapper: ImageNetworkMapper
    ): RandomImageRepository =
        RandomImageRepository(
            unsplashAPI,
            randomImageDao,
            imageCacheMapper,
            imageNetworkMapper
        )

    @Singleton
    @Provides
    fun provideImageByIdRepository(
        unsplashAPI: UnsplashAPI,
        imageNetworkMapper: ImageNetworkMapper

    ): DetailImageRepository = DetailImageRepository(unsplashAPI, imageNetworkMapper)

    @Singleton
    @Provides
    fun provideCollectionRepository(
        unsplashAPI: UnsplashAPI,
        collectionNetworkMapper: CollectionNetworkMapper

    ): CollectionRepository = CollectionRepository(unsplashAPI, collectionNetworkMapper)

    @Singleton
    @Provides
    fun provideImageByCollectionRepository(
        unsplashAPI: UnsplashAPI,
        imageNetworkMapper: ImageNetworkMapper

    ): ImageByCollectionRepository = ImageByCollectionRepository(unsplashAPI, imageNetworkMapper)

    @Singleton
    @Provides
    fun provideSearchImageRepository(
        unsplashAPI: UnsplashAPI,
       searchImagesNetworkMapper: SearchImagesNetworkMapper
    ): SearchImageRepository = SearchImageRepository(unsplashAPI, searchImagesNetworkMapper)
}