package com.spacepal.internal.app

import android.app.Application
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.roadhourse.spacepal.ui.login.LoginActivity
import com.spacepal.internal.app.event.LoginFailEvent
import com.spacepal.internal.app.model.SpacePalAuthenticator
import com.spacepal.internal.app.model.SpacePalAuthorizationInterceptor
import com.spacepal.internal.app.source.Api
import com.spacepal.internal.app.source.IdentityApi
import com.spacepal.internal.app.util.PreferenceUtil
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpacePalApplication : Application(), AppLifecycleHandler.LifeCycleDelegate {

    companion object {
        lateinit var instance: SpacePalApplication
    }

    private var lifeCycleHandler: AppLifecycleHandler? = null
    private var isForeground = true

    lateinit var api: Api
        private set
    lateinit var identityApi: IdentityApi
        private set

    lateinit var gson: Gson

    override fun onCreate() {
        super.onCreate()
        instance = this
        EventBus.getDefault().register(this)
        Realm.init(this)

        val realmConfiguration = RealmConfiguration.Builder()
                .name("Realm.SpacePal")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        lifeCycleHandler =  AppLifecycleHandler(this)

        val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(SpacePalAuthorizationInterceptor())
                .authenticator(SpacePalAuthenticator())

        if(BuildConfig.DEBUG){
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        val client = okHttpClientBuilder.build()
        gson = GsonBuilder().setExclusionStrategies(AnnotationExclusionStrategy()).create()

        api = Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(Api::class.java)

        identityApi = Retrofit.Builder()
                .baseUrl(getString(R.string.identity_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(IdentityApi::class.java)
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
}
