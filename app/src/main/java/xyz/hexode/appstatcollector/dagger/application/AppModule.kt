package xyz.hexode.appstatcollector.dagger.application

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.hexode.appstatcollector.AppListActivity
import xyz.hexode.appstatcollector.dagger.activity.AppListActivityModule
import xyz.hexode.appstatcollector.dagger.activity.AppListActivityScope

@Module
abstract class AppModule {
    @ContributesAndroidInjector(modules = [AppListActivityModule::class])
    @AppListActivityScope
    abstract fun contributeActivityInjector(): AppListActivity
}
