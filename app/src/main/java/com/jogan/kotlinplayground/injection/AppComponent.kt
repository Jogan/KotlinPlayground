package com.jogan.kotlinplayground.injection

import com.jogan.kotlinplayground.PlaygroundApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<PlaygroundApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PlaygroundApplication>()
}