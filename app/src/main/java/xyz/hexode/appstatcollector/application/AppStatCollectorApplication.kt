package xyz.hexode.appstatcollector.application

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import xyz.hexode.appstatcollector.dagger.application.DaggerAppComponent

class AppStatCollectorApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}
