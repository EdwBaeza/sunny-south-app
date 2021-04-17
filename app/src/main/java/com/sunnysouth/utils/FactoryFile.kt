package com.sunnysouth.utils

import android.content.Context
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI

class FactoryFile {

    companion object {
        fun buildMultiPartFormData(uri: String?, paramName: String, mediaType: String): MultipartBody.Part {
            val file = File(uri)
            val requestFile = RequestBody.create(MediaType.parse(mediaType), file)

            return MultipartBody.Part.createFormData(paramName, file.name, requestFile)
        }
    }
}