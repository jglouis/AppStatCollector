package xyz.hexode.appstatcollector.dagger.application

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.hexode.appstatcollector.AppListActivity
import xyz.hexode.appstatcollector.application.AppStatCollectorApplication
import xyz.hexode.appstatcollector.dagger.activity.AppListActivityModule
import xyz.hexode.appstatcollector.dagger.activity.AppListActivityScope

@Module
abstract class AppModule {
    @ContributesAndroidInjector(modules = [AppListActivityModule::class])
    @AppListActivityScope
    abstract fun contributeActivityInjector(): AppListActivity

    @Binds
    abstract fun application(application: AppStatCollectorApplication) : Context
}
