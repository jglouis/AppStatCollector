package xyz.hexode.appstatcollector.dagger.activity

import android.app.Activity
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import xyz.hexode.appstatcollector.activity.AppListActivity
import xyz.hexode.appstatcollector.adapter.CardClickListener

@Module
class AppListActivityModule {
    @Provides
    @AppListActivityScope
    fun provideActivity(appListActivity: AppListActivity): Activity = appListActivity

    @Provides
    @AppListActivityScope
    fun provideCardClickListener(appListActivity: AppListActivity): CardClickListener = appListActivity

    @Provides
    fun provideApplications(activity: Activity): MutableList<ApplicationInfo> =
        activity.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
}
