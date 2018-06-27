package com.spacepal.internal.app.model

import android.util.Log
import com.spacepal.internal.app.SpacePalApplication
import com.spacepal.internal.app.event.LoginFailEvent
import com.spacepal.internal.app.util.PreferenceUtil
import com.spacepal.internal.app.util.Util
import okhttp3.Interceptor
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import java.io.IOException

class SpacePalAuthorizationInterceptor() : Interceptor {

    private val TAG = "Retrofit"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        // header will be access_token
        val tokenResponse = PreferenceUtil.getInstance(SpacePalApplication.instance).tokenObj
        val header = Util.getAuthorizationHeader(tokenResponse)
        if (header != null) {
            val request = original.newBuilder()
                    .header("Authorization", "Bearer " + header!!).build()


            Log.d(TAG, "intercept: add header " + header!!)
            val response = chain.proceed(request)
            if (response.code() == 401) {
                EventBus.getDefault().post(LoginFailEvent())
            }

            return response
        } else {
            Log.d(TAG, "intercept: add no header")
            val response = chain.proceed(original)
            return response
        }

    }
}