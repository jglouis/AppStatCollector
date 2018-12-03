package xyz.hexode.appstatcollector.util.picasso

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import com.squareup.picasso.RequestHandler
import xyz.hexode.appstatcollector.util.toBitmap
import java.io.IOException


class AppIconRequestHandler(context: Context) : RequestHandler() {

    private val pm: PackageManager by lazy {
        context.packageManager
    }
    private val dpi: Int by lazy {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        am.launcherLargeIconDensity
    }
    private val defaultAppIcon: Bitmap by lazy {
        @SuppressLint("ObsoleteSdkInt")
        val drawable: Drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            @Suppress("DEPRECATION")
            Resources.getSystem().getDrawableForDensity(
                android.R.mipmap.sym_def_app_icon, dpi
            )!!
        } else {
            @Suppress("DEPRECATION")
            Resources.getSystem().getDrawable(
                android.R.drawable.sym_def_app_icon
            )
        }
        drawable.toBitmap()
    }

    override fun canHandleRequest(data: Request): Boolean {
        return data.uri != null && TextUtils.equals(data.uri.scheme, SCHEME_PNAME)
    }


    @Throws(IOException::class, PackageManager.NameNotFoundException::class)
    override fun load(request: Request, networkPolicy: Int): RequestHandler.Result {
        return RequestHandler.Result(getFullResIcon(request.uri.toString().split(":")[1]), Picasso.LoadedFrom.DISK)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    private fun getFullResIcon(packageName: String): Bitmap {
        return getFullResIcon(pm.getApplicationInfo(packageName, 0))
    }

    private fun getFullResIcon(info: ApplicationInfo): Bitmap {
        try {
            val resources = pm.getResourcesForApplication(info.packageName)
            if (resources != null) {
                val iconId = info.icon
                if (iconId != 0) {
                    return getFullResIcon(resources, iconId)
                }
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }

        return defaultAppIcon
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun getFullResIcon(resources: Resources, iconId: Int): Bitmap {
        val drawable: Drawable = try {
            @Suppress("DEPRECATION")
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 -> resources.getDrawableForDensity(
                    iconId,
                    dpi,
                    null
                )!!
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 -> resources.getDrawableForDensity(
                    iconId,
                    dpi
                )!!
                else -> resources.getDrawable(iconId)
            }
        } catch (e: Resources.NotFoundException) {
            return defaultAppIcon
        }

        return drawable.toBitmap()
    }

    companion object {
        const val SCHEME_PNAME = "pname"
    }

}