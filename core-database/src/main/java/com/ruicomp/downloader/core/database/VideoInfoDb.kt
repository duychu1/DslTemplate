package com.ruicomp.downloader.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VideoInfoEntity::class], version = 1)
abstract class VideoInfoDb : RoomDatabase() {
    abstract fun getVideoInfoDao() : VideoInfoDao
}

//companion object {
//    // Singleton prevents multiple
//    // instances of database opening at the
//    // same time.
//    @Volatile
//    private var INSTANCE: VideoInfoDb? = null
//
//    fun getDatabase(context: Context): VideoInfoDb {
//        // if the INSTANCE is not null, then return it,
//        // if it is, then create the database
//        return INSTANCE ?: synchronized(this) {
//            val instance = Room.databaseBuilder(
//                context,
//                VideoInfoDb::class.java,
//                DB_NAME
//            ).build()
//            INSTANCE = instance
//            // return instance
//            instance
//        }
//    }
//}