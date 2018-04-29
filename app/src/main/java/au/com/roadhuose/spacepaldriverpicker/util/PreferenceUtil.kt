package au.com.roadhuose.spacepaldriverpicker.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import au.com.roadhuose.spacepaldriverpicker.model.User
import au.com.roadhuose.spacepaldriverpicker.model.response.TokenResponse
import com.google.gson.Gson

class PreferenceUtil private constructor(context: Context) {

    private val sPref: SharedPreferences

    var isSignIn: Boolean
        get() = sPref.getBoolean(KEY_IS_SIGN_IN, false)
        set(value) {
            val editor = sPref.edit()
            editor.putBoolean(KEY_IS_SIGN_IN, value)
            editor.apply()
        }

    val username: String
        get() = sPref.getString(KEY_EMAIL, "")

    val password: String
        get() = sPref.getString(KEY_PASSWORD, "")

    val account: User
        get() {
            val gson = Gson()
            var user: User? = gson.fromJson<Any>(sPref.getString(KEY_USER, ""), User::class.java) as User?

            if (user == null) {
                user = User()
            }
            return user
        }

    val tokenObj: TokenResponse
        get() {
            val gson = Gson()
            var tokenResponse: TokenResponse? = gson.fromJson<Any>(sPref.getString(KEY_TOKEN, ""), TokenResponse::class.java) as TokenResponse?

            if (tokenResponse == null) {
                tokenResponse = TokenResponse()
            }
            return tokenResponse
        }

    init {
        sPref = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    }

    fun saveEmail(email: String) {
        val editor = sPref.edit()
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }

    fun savePassword(password: String) {
        val editor = sPref.edit()
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }

    fun saveAccount(user: User?) {
        val gson = Gson()
        val str = gson.toJson(user)
        val editor = sPref.edit()
        editor.putString(KEY_USER, str)
        editor.apply()
    }

    fun saveTokenObject(tokenObj: TokenResponse?) {
        val gson = Gson()
        val str = gson.toJson(tokenObj)
        val editor = sPref.edit()
        editor.putString(KEY_TOKEN, str)
        editor.apply()
    }

    fun savePreference(key: String, value: String) {
        val editor = sPref.edit()
        editor.putString(key, value)
        editor.apply()
    }


    fun getPreference(key: String): String {
        return sPref.getString(key, "")
    }

    fun clearAllPreferences() {

        val editor = sPref.edit()
        editor.clear()
        editor.apply()

    }

    companion object {


        val KEY_IS_SIGN_IN = "is_sign_in"
        val KEY_EMAIL = "email"
        val KEY_PASSWORD = "password"
        val KEY_USER = "user"
        val KEY_TOKEN = "token_object"
        private val PREFERENCE_NAME = "send_signal_preference"

        private var instance: PreferenceUtil? = null
        fun getInstance(context: Context): PreferenceUtil {
            if (instance == null) {
                instance = PreferenceUtil(context)
            }
            return instance as PreferenceUtil
        }
    }

}
