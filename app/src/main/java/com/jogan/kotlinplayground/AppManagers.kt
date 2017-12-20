package com.jogan.kotlinplayground

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import javax.inject.Inject

class AppManagers(private vararg val managers: AppManager) : AppManager {
    override fun init(application: Application) {
        managers.forEach {
            it.init(application)
        }
    }
}

class LeakCanaryManager @Inject constructor() : AppManager {
    override fun init(application: Application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application)
        }
    }
}

class TimberManager @Inject constructor() : AppManager {
    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}