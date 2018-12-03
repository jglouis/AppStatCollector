package xyz.hexode.appstatcollector

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        holder.packageNameTextView.text = applicationInfo.packageName

        val isAppActive = (context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager)?.let {
            !it.isAppInactive(applicationInfo.packageName)
        }
        holder.activityIndicatorImageView.setImageResource(
            when (isAppActive) {
                true -> R.drawable.ic_play
                false -> R.drawable.ic_stop
                null -> R.drawable.ic_question_mark
            }
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val packageNameTextView = itemView.findViewById<TextView>(R.id.app_item_package_name)!!
        val activityIndicatorImageView = itemView.findViewById<ImageView>(R.id.app_item_isActive)!!
    }
}
