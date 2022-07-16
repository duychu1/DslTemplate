package com.ruicomp.downloader.download.video

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val msg:String, val exception: Exception? = null) : Result<Nothing>()
}
