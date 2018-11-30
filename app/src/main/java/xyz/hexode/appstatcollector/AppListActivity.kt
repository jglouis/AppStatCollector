package xyz.hexode.appstatcollector

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_app_list.*
import kotlinx.android.synthetic.main.content_app_list.*
import javax.inject.Inject

class AppListActivity : AppCompatActivity() {

    @Inject lateinit var appListAdapter: AppListAdapter

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
}
