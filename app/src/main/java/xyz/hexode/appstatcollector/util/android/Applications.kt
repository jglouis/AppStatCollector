package xyz.hexode.appstatcollector.util.android

import android.content.Context
import android.content.pm.PackageManager

const val SYSTEM_PACKAGE_NAME = "android"


/**
 * Match signature of application to identify that if it is signed by system
 * or not.
 *
 * @param packageName
 *            package of application. Can not be blank.
 * @return <code>true</code> if application is signed by system certificate,
 *         otherwise <code>false</code>
 */
fun Context.isSystemApp(packageName: String): Boolean {
    return try {
        // Get packageinfo for target application
        val targetPkgInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        // Get packageinfo for system package
        val sys = packageManager.getPackageInfo(SYSTEM_PACKAGE_NAME, PackageManager.GET_SIGNATURES)
        // Match both packageinfo for there signatures
        targetPkgInfo?.signatures != null && sys.signatures[0] == targetPkgInfo.signatures[0]
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}
