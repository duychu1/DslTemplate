package com.ruicomp.downloader.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruicomp.downloader.core.model.VideoInfo

const val DB_NAME = "videos_info_fb"

@Entity(tableName = DB_NAME)
data class VideoInfoEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,

    @ColumnInfo(name = "title") val title:String,

    @ColumnInfo(name = "uri") val uri: String,

    @ColumnInfo(name = "duration") val duration: String,

)

fun VideoInfoEntity.asModel(): VideoInfo {
    return VideoInfo(
        id, title, uri, duration
    )
}
