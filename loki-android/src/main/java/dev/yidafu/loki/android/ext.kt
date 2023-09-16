package dev.yidafu.loki.android

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log

fun Context.getAppName(): String {
    return applicationInfo.loadLabel(packageManager).toString()
}

fun Context.getMetaDataString(name: String, defaultValue: String): String {
    try {
        val ai = packageManager.getApplicationInfo(
            packageName,
            PackageManager.GET_META_DATA,
        )
        val bundle = ai.metaData
        return bundle.getString(name) ?: defaultValue
    } catch (e: PackageManager.NameNotFoundException) {
        Log.w("LokiAndroid", "Unable to load meta-data: " + e.message)
    }
    return defaultValue
}

fun Context.getMetaDataInt(name: String, defaultValue: Int): Int {
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
