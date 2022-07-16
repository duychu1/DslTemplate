package com.ruicomp.downloader.core.database.di

import android.content.Context
import androidx.room.Room
import com.ruicomp.downloader.core.database.DB_NAME
import com.ruicomp.downloader.core.database.VideoInfoDao
import com.ruicomp.downloader.core.database.VideoInfoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDbModule {

    @Provides
    @Singleton
    fun provideVideoInfoDb(@ApplicationContext applicationContext: Context): VideoInfoDb {
        return Room.databaseBuilder(
            applicationContext,
            VideoInfoDb::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideVideoInfoDao(db: VideoInfoDb): VideoInfoDao {
        return db.getVideoInfoDao()
    }
}