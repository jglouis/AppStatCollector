package xyz.hexode.appstatcollector.dagger.application

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import xyz.hexode.appstatcollector.application.AppStatCollectorApplication

@Component(modules = [AndroidInjectionModule::class, AppModule::class])
interface AppComponent : AndroidInjector<AppStatCollectorApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AppStatCollectorApplication>()
}
