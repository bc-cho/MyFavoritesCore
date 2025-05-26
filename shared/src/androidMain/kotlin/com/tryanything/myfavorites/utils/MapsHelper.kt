package com.tryanything.myfavorites.utils

import android.content.Context
import android.content.pm.PackageManager

actual open class MapsHelper {

    actual open fun getApiKey(): String = ""
}

class MapsHelperImpl(val context: Context) : MapsHelper() {

    val mapApiKey: String

    init {
        val metaData = kotlin.runCatching {
            context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            ).metaData
        }.getOrNull()
        mapApiKey = metaData?.getString("com.google.android.geo.API_KEY")
            ?: throw IllegalStateException("Map Api key is not configured")
    }

    override fun getApiKey(): String = mapApiKey
}
