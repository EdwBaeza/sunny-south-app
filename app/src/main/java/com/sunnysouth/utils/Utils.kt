package com.sunnysouth.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri

fun getRealPath(context: Context?, uri: Uri): String? {
    var cursor: Cursor? = null
    val column = "_data"

    try {
        cursor = context?.contentResolver?.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndexOrThrow(column))
        }
    } finally {
        cursor?.close()
    }

    return null
}