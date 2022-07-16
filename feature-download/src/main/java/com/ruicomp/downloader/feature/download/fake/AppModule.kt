package com.ruicomp.downloader.feature.download.fake

import android.content.Context
import com.ruicomp.downloader.feature.download.repository.DownloadVideoRepository
import com.ruicomp.downloader.feature.download.repository.DownloadVideoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideManagerAds(): ManagerInterAds = ManagerInterAds()

    @Provides
    @Singleton
    fun provideIap(@ApplicationContext context: Context): Iap = Iap()

    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideDownloadVideoRepository(): DownloadVideoRepository {
        return DownloadVideoRepositoryImpl()
    }
}

