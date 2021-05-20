package com.study.coroutinedemo

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import java.io.InputStream

object VideoUtil {

    private const val TAG = "VideoUtil"

    @JvmStatic
    fun writeVideoToStorage(context: Context): Boolean {
        Log.d(TAG, "writeVideoToStorage: startTime=${System.currentTimeMillis()}")
        var result = false

        try {
            val url = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

            val videoContent = ContentValues().apply {
                put(MediaStore.Video.Media.DISPLAY_NAME, "video-${System.currentTimeMillis()}.mov")
            }

            val uri = context.contentResolver.insert(url, videoContent)
            if (uri != null) {
                val outputStream = context.contentResolver.openOutputStream(uri)

                if (outputStream != null) {
                    getVideoContent(context).copyTo(outputStream)

                    result = true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Log.d(TAG, "writeVideoToStorage: endTime=${System.currentTimeMillis()}")
        return result
    }

    private fun getVideoContent(context: Context): InputStream {
        return context.resources.assets.open("video2.mov")
    }
}