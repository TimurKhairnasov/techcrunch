package com.example.schooluser.test

import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.soloader.SoLoader

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SoLoader.init(this, false)
        Fresco.initialize(this)
    }
}