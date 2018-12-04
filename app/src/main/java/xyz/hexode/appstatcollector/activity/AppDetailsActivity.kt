package xyz.hexode.appstatcollector.activity

import android.content.pm.ApplicationInfo
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_app_details.*
import xyz.hexode.appstatcollector.R
import xyz.hexode.appstatcollector.util.picasso.updateLaunchIcon

class AppDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app_details)
//        supportPostponeEnterTransition()

        intent?.extras?.let {
            app_details_launch_icon.transitionName =
                    it.getString(AppListActivity.INTENT_EXTRA_APP_ICON_TRANSITION_NAME)
            val uri = Uri.parse(it.getString(AppListActivity.INTENT_EXTRA_APP_ICON_URI))
            app_details_launch_icon.updateLaunchIcon(uri)
            app_details_package_name.text = it.getString(AppListActivity.INTENT_EXTRA_PACKAGE_NAME)
        }
    }
}