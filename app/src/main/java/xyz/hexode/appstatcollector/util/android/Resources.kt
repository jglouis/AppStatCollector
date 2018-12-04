package xyz.hexode.appstatcollector.util.android

import android.content.ContentResolver
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.net.Uri

fun Resources.getUri(resourceId: Int): Uri =
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(getResourcePackageName(resourceId))
        .appendPath(getResourceTypeName(resourceId))
        .appendPath(getResourceEntryName(resourceId))
        .build()

fun Context.getLaunchIconUri(applicationInfo: ApplicationInfo) : Uri? {
    val resources = packageManager.getResourcesForApplication(applicationInfo.packageName)
    val resourceId = applicationInfo.icon
    return if (resources != null && resourceId != 0) {
        resources.getUri(resourceId)
    } else null
}
