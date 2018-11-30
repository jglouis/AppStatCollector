package xyz.hexode.appstatcollector

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_app_list.*
import kotlinx.android.synthetic.main.content_app_list.*

class AppListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)
        setSupportActionBar(toolbar)

        val applications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        recyclerViewAppList.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AppListActivity)
            adapter = AppListAdapter(applications)
        }
    }
}
