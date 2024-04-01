package dev.yidafu.loki.android

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log

internal fun Context.getAppName(): String {
    return applicationInfo.loadLabel(packageManager).toString()
}

private inline fun Context.getMetaDate(): Bundle? {
    val appInfo = packageManager.getApplicationInfo(
        packageName,
        PackageManager.GET_META_DATA,
    )
    return appInfo.metaData
}

internal fun Context.getMetaDataString(name: String, defaultValue: String): String {
    try {
        val bundle = getMetaDate()
        return bundle?.getString(name, defaultValue) ?: defaultValue
    } catch (e: PackageManager.NameNotFoundException) {
        Log.w("LokiAndroid", "Unable to load meta-data '$name' " + e.message)
    }
    return defaultValue
}

internal fun Context.getMetaDataInt(name: String, defaultValue: Int): Int {
    try {
        val bundle = getMetaDate()
        return bundle?.getInt(name, defaultValue) ?: defaultValue
    } catch (e: PackageManager.NameNotFoundException) {
        Log.w("LokiAndroid", "Unable to load meta-data: " + e.message)
    }
    return defaultValue
}

internal fun Context.getMetaDataLong(name: String, defaultValue: Long): Long {
    try {
        val bundle = getMetaDate()
        return bundle?.getLong(name, defaultValue) ?: defaultValue
    } catch (e: PackageManager.NameNotFoundException) {
        Log.w("LokiAndroid", "Unable to load meta-data: " + e.message)
    }
    return defaultValue
}

/**
 * 获取versionCode
 */
internal fun Context.getVersionCode(): Long {
    val packageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        return packageInfo.versionCode.toLong()
    }
}