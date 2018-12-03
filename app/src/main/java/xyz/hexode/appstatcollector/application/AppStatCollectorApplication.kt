package xyz.hexode.appstatcollector.application

import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import xyz.hexode.appstatcollector.dagger.application.DaggerAppComponent
import xyz.hexode.appstatcollector.util.picasso.AppIconRequestHandler

class AppStatCollectorApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        Picasso.setSingletonInstance(
            Picasso.Builder(this)
                .addRequestHandler(AppIconRequestHandler(this))
                .build()
        )
    }
}