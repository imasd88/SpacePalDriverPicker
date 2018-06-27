package com.spacepal.internal.app.model

import android.util.Log
import com.google.gson.Gson
import com.spacepal.internal.app.R
import com.spacepal.internal.app.SpacePalApplication
import com.spacepal.internal.app.model.response.TokenResponse
import com.spacepal.internal.app.util.PreferenceUtil
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class SpacePalAuthenticator() : Authenticator {
    private val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    internal var gson = Gson()

    @Throws(IOException::class)
    override fun authenticate(route: Route, response: Response): Request? {
        var request: Request? = null
        try {
            val refreshToken = PreferenceUtil.getInstance(SpacePalApplication.instance).tokenObj.refreshToken
            val urlToken = SpacePalApplication.instance.getString(R.string.identity_url)
            val refreshTokenRequest = Request.Builder()
                    .url("$urlToken/connect/token") // uncomment: either place base_url in string as here or make your own url
                    .post(FormBody.Builder()
                            .add("grant_type", "refresh_token")
                            .add("refresh_token", refreshToken) // uncomment: add refreshtoken here
                            .add("client_id", "spacePAL.admin")
                            .add("client_secret", "8cef9b1d-973b-4d70-bd73-623e4d5782b2")
                            .build())
                    .build()
            val result = client.newCall(refreshTokenRequest).execute()
            val resultBody = result.body()

            if (result.isSuccessful && resultBody != null) {
                val authResult = gson.fromJson<Any>(resultBody.string(), TokenResponse::class.java)
                PreferenceUtil.getInstance(SpacePalApplication.instance).saveTokenObject(authResult as TokenResponse)


                request = response.request().newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", authResult.tokenType + " " + authResult.accessToken)
                        .build()


            }

        } catch (e: Exception) {
            //            Timber.w(e) { "Failed to refresh auth token" }
            Log.e("something bad happened", e.toString())
        }

        if (request == null) {
            //While all credentials as we no longer have a valid user
            PreferenceUtil.getInstance(SpacePalApplication.instance).tokenObj.accessToken = ""
            PreferenceUtil.getInstance(SpacePalApplication.instance).tokenObj.refreshToken = ""
        }

        return request
    }
}