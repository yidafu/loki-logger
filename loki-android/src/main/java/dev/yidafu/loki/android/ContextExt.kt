package dev.yidafu.loki.android

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log

internal fun Context.getAppName(): String {
    return applicationInfo.loadLabel(packageManager).toString()
}

internal fun Context.getMetaDataString(name: String, defaultValue: String): String {
    try {
        val appInfo = packageManager.getApplicationInfo(
            packageName,
            PackageManager.GET_META_DATA,
        )
        val bundle = appInfo.metaData
        return bundle.getString(name) ?: defaultValue
    } catch (e: PackageManager.NameNotFoundException) {
        Log.w("LokiAndroid", "Unable to load meta-data '$name' " + e.message)
    }
    return defaultValue
}

internal fun Context.getMetaDataInt(name: String, defaultValue: Int): Int {
    try {
        val ai = packageManager.getApplicationInfo(
            packageName,
            PackageManager.GET_META_DATA,
        )
        val bundle = ai.metaData
        return bundle.getInt(name) ?: defaultValue
    } catch (e: PackageManager.NameNotFoundException) {
        Log.w("LokiAndroid", "Unable to load meta-data: " + e.message)
    }
    return defaultValue
}

internal fun Context.getMetaDataLong(name: String, defaultValue: Long): Long {
    try {
        val ai = packageManager.getApplicationInfo(
            packageName,
            PackageManager.GET_META_DATA,
        )
        val bundle = ai.metaData
        return bundle.getLong(name, defaultValue)
    } catch (e: PackageManager.NameNotFoundException) {
        Log.w("LokiAndroid", "Unable to load meta-data: " + e.message)
    }
    return defaultValue
}