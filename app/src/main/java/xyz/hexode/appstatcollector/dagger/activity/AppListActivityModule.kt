package xyz.hexode.appstatcollector.dagger.activity

import android.app.Activity
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import xyz.hexode.appstatcollector.AppListActivity
import xyz.hexode.appstatcollector.AppListAdapter

@Module
class AppListActivityModule {
    @Provides
    @AppListActivityScope
    fun provideActivity(appListActivity: AppListActivity): Activity = appListActivity

    @Provides
    @AppListActivityScope
    fun provideAppListAdapter(applications: MutableList<ApplicationInfo>): AppListAdapter = AppListAdapter(applications)

    @Provides
    fun provideApplications(activity: Activity): MutableList<ApplicationInfo> =
        activity.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
}
