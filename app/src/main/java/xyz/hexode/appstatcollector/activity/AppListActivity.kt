package xyz.hexode.appstatcollector.activity

import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_app_list.*
import kotlinx.android.synthetic.main.content_app_list.*
import xyz.hexode.appstatcollector.R
import xyz.hexode.appstatcollector.adapter.AppListAdapter
import xyz.hexode.appstatcollector.adapter.CardClickListener
import xyz.hexode.appstatcollector.util.android.getLaunchIconUri
import javax.inject.Inject

class AppListActivity : AppCompatActivity(), CardClickListener {

    companion object {
        const val INTENT_EXTRA_PACKAGE_NAME = "INTENT_EXTRA_PACKAGE_NAME"
        const val INTENT_EXTRA_APP_ICON_URI = "INTENT_EXTRA_APP_ICON_URI"
        const val INTENT_EXTRA_APP_ICON_TRANSITION_NAME = "INTENT_EXTRA_APP_ICON_TRANSITION_NAME"
    }

    @Inject
    lateinit var appListAdapter: AppListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app_list)
        setSupportActionBar(toolbar)

        recyclerViewAppList.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AppListActivity)
            adapter = appListAdapter
        }
    }

    override fun onApplicationCardClick(applicationInfo: ApplicationInfo, sharedImageView: ImageView) {
        val intent = Intent(this, AppDetailsActivity::class.java)
            .apply {
                putExtra(INTENT_EXTRA_PACKAGE_NAME, applicationInfo.packageName)
                putExtra(INTENT_EXTRA_APP_ICON_URI, getLaunchIconUri(applicationInfo).toString())
                putExtra(INTENT_EXTRA_APP_ICON_TRANSITION_NAME, sharedImageView.transitionName)
            }
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            sharedImageView.transitionName
        )
        startActivity(intent, options.toBundle())
    }
}
