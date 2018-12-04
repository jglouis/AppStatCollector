package xyz.hexode.appstatcollector.adapter

import android.content.pm.ApplicationInfo
import android.widget.ImageView

interface CardClickListener {
    fun onApplicationCardClick(applicationInfo: ApplicationInfo, sharedImageView: ImageView)
}
