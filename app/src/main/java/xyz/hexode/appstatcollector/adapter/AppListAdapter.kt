package xyz.hexode.appstatcollector.adapter

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import xyz.hexode.appstatcollector.R
import xyz.hexode.appstatcollector.util.android.getLaunchIconUri
import xyz.hexode.appstatcollector.util.android.isSystemApp
import xyz.hexode.appstatcollector.util.picasso.updateLaunchIcon
import javax.inject.Inject

class AppListAdapter @Inject constructor(
    private val context: Context,
    private val cardClickListener: CardClickListener,
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

    init {
        // Sort the applications by alphabetical order
        applications.sortBy { it.packageName }
    }

    override fun getItemCount() = applications.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val applicationInfo = applications[position]
        holder.updatePackageName(applicationInfo.packageName)
        holder.updateLaunchIcon(applicationInfo)
        val isAppActive = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            (context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager)?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    !it.isAppInactive(applicationInfo.packageName)
                } else null
            }
        } else null
        holder.updateIndicator(isAppActive)
        holder.updateSystemIconVisibility(context.isSystemApp(applicationInfo.packageName))

        holder.appLaunchIconImageView.transitionName = holder.adapterPosition.toString()
        holder.itemView.setOnClickListener {
            cardClickListener.onApplicationCardClick(
                applicationInfo,
                holder.appLaunchIconImageView
            )
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appLaunchIconImageView = itemView.findViewById<ImageView>(R.id.app_item_launch_icon)!!
        private val packageNameTextView = itemView.findViewById<TextView>(R.id.app_item_package_name)!!
        private val activityIndicatorImageView = itemView.findViewById<ImageView>(R.id.app_item_isActive)!!
        private val appItemSystemIconImageView = itemView.findViewById<ImageView>(R.id.app_item_system_icon)!!

        fun updatePackageName(packageName: String) {
            packageNameTextView.text = packageName
        }

        fun updateLaunchIcon(applicationInfo: ApplicationInfo) {
            val uri = context.getLaunchIconUri(applicationInfo)
            if (uri != null)
                appLaunchIconImageView.updateLaunchIcon(uri)
            else
                appLaunchIconImageView.setImageResource(android.R.drawable.sym_def_app_icon)
        }

        fun updateIndicator(isAppActive: Boolean?) {
            val indicator = when (isAppActive) {
                true -> R.drawable.ic_play
                false -> R.drawable.ic_stop
                null -> R.drawable.ic_question_mark
            }
            activityIndicatorImageView.setImageResource(indicator)
        }

        fun updateSystemIconVisibility(isSystemApp: Boolean) {
            appItemSystemIconImageView.visibility = if (isSystemApp) View.VISIBLE else View.INVISIBLE
        }
    }
}
