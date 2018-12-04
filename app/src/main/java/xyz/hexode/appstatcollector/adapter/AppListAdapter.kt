package xyz.hexode.appstatcollector.adapter

import android.app.usage.UsageStatsManager
import android.content.ContentResolver
import android.content.Context
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import xyz.hexode.appstatcollector.R
import java.lang.Exception
import javax.inject.Inject

class AppListAdapter @Inject constructor(
    private val context: Context,
    private val applications: MutableList<ApplicationInfo>
) :
    RecyclerView.Adapter<AppListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.app_item, parent, false)
        )
    }

    override fun getItemCount() = applications.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val applicationInfo = applications[position]
        holder.updatePackageName(applicationInfo.packageName)
        holder.updateLaunchIcon(applicationInfo)
        val isAppActive = (context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager)?.let {
            !it.isAppInactive(applicationInfo.packageName)
        }
        holder.updateIndicator(isAppActive)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val appLaunchIconImageView = itemView.findViewById<ImageView>(R.id.app_item_launch_icon)!!
        private val packageNameTextView = itemView.findViewById<TextView>(R.id.app_item_package_name)!!
        private val activityIndicatorImageView = itemView.findViewById<ImageView>(R.id.app_item_isActive)!!

        fun updatePackageName(packageName: String) {
            packageNameTextView.text = packageName
        }

        fun updateLaunchIcon(applicationInfo: ApplicationInfo) {
            applicationInfo.icon

            val resources = context.packageManager.getResourcesForApplication(applicationInfo.packageName)
            val resourceId = applicationInfo.icon
            if (resources != null && resourceId != 0) {
                val uri = Uri.Builder()
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .authority(resources.getResourcePackageName(resourceId))
                    .appendPath(resources.getResourceTypeName(resourceId))
                    .appendPath(resources.getResourceEntryName(resourceId))
                    .build()
                Picasso.get()
                    .load(uri)
                    .into(appLaunchIconImageView, object : Callback {
                        override fun onSuccess() = Unit
                        override fun onError(e: Exception?) {
                            appLaunchIconImageView.setImageURI(uri)
                        }
                    })
            } else {
                appLaunchIconImageView.setImageResource(android.R.drawable.sym_def_app_icon)
            }
        }

        fun updateIndicator(isAppActive: Boolean?) {
            val indicator = when (isAppActive) {
                true -> R.drawable.ic_play
                false -> R.drawable.ic_stop
                null -> R.drawable.ic_question_mark
            }
            activityIndicatorImageView.setImageResource(indicator)
        }
    }
}
