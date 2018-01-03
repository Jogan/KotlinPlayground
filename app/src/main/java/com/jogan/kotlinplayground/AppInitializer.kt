package com.jogan.kotlinplayground

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}