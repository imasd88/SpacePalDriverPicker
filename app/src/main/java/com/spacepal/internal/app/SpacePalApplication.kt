package com.spacepal.internal.app

import android.app.Application
import android.content.Intent
import android.util.Log
import com.roadhourse.spacepal.ui.login.LoginActivity
import com.spacepal.internal.app.event.LoginFailEvent
import com.spacepal.internal.app.util.PreferenceUtil
import io.realm.Realm
import io.realm.RealmConfiguration
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SpacePalApplication : Application(), AppLifecycleHandler.LifeCycleDelegate {
    private var lifeCycleHandler: AppLifecycleHandler? = null
    private var isForeground = true
        private set

    override fun onCreate() {
        super.onCreate()
        insta = this
        EventBus.getDefault().register(this)
        Realm.init(this)

        val realmConfiguration = RealmConfiguration.Builder()
                .name("Realm.SpacePal")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        lifeCycleHandler =  AppLifecycleHandler(this)
      //  registerLifecycleHandler(lifeCycleHandler!!)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: LoginFailEvent) {
        if (PreferenceUtil.getInstance(this).account.isSignIn) {
            logout()
        }
    }


     fun logout() {
        PreferenceUtil.getInstance(this).clearAllPreferences()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onAppBackgrounded() {
        isForeground = false
        Log.d("SpacePalApplication", " App in background")
    }

    override fun onAppForegrounded() {
        isForeground = true
        Log.d("SpacePalApplication", " App in foreground")
    }

    private fun registerLifecycleHandler(lifeCycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }

    companion object {
        private var insta: SpacePalApplication? = null

        fun getInstance(): SpacePalApplication {
            return if (insta == null) SpacePalApplication() else insta!!
        }
    }
}
