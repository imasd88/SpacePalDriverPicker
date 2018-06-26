package com.spacepal.internal.app.ui.login

import com.spacepal.internal.app.Constant
import com.spacepal.internal.app.Constant.*
import com.spacepal.internal.app.model.Profile
import com.spacepal.internal.app.model.response.TokenResponse
import com.spacepal.internal.app.source.RetrofitHelper
import com.spacepal.internal.app.util.PreferenceUtil
import com.spacepal.internal.app.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val loginView: LoginContract.View, private var preferenceUtil: PreferenceUtil) : LoginContract.Presenter, Constant {

    init {
        this.loginView.setPresenter(this)
    }

    override fun start() {
    }

    override fun signIn() {
//        loginView.showProgressDialog(true)

        val call = RetrofitHelper.instance!!.api.account
        call.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
//                loginView.showProgressDialog(false)
                if (response.code() == 200) {
                    loginView.userRetrieved(response.body()!!)
                    loginView.signInIsSuccessful(response.body()!!)
                    loginView.showProgressDialog(false)
                } else {

                    val error = Util.parseError(response)
                    loginView.showMessage(error.getError()!!, true)
                    loginView.showProgressDialog(false)
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                loginView.showProgressDialog(false)
                loginView.showMessage("Fail...", true)
                t.printStackTrace()
            }
        })


    }

    override fun getToken(username: String, password: String) {
        preferenceUtil.saveTokenObject(null)
        if (username == null || username.trim { it <= ' ' }.isEmpty() || !username.contains("@")) {
            loginView.showErrorOnEmail("Please enter a valid email", true)
            return
        } else {
            loginView.showErrorOnEmail("", false)
        }

        if (password == null || password.trim { it <= ' ' }.isEmpty()) {
            loginView.showErrorOnPassword("Please enter a password", true)
            return
        } else {
            loginView.showErrorOnPassword("", false)
        }

        loginView.showProgressDialog(true)

        val call = RetrofitHelper.instanceForToken!!.api.getToken(username, password, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE)
        call.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
//                loginView.showProgressDialog(false)
                if (response.code() == 200) {
                    preferenceUtil.savePassword(password);
                    preferenceUtil.saveEmail(username);
                    loginView.tokenRetrieved(response.body()!!)

                } else {
                    val error = Util.parseError(response)
                    when (error.getErrorCode()) {
                        "PASSWORD_INVALID" -> loginView.showErrorOnPassword("Invalid Password ${error.getRemTryAllowed()} tries remaining", true)
                        "USER_NOT_FOUND" -> loginView.showErrorOnEmail("Invalid email", true)
                        "USER_LOCKOUT" -> loginView.showMessage("We've locked your account after too many failed attempts to sign in. Contact your administrator to unlock your account.", true)
                        "USER_DISABLED" -> loginView.showMessage("Your account has been disabled. Contact your administrator to activate your account.", true)
                    }
                    loginView.showProgressDialog(false)
                }
//                loginView.showProgressDialog(false)
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                loginView.showProgressDialog(false)
                loginView.showMessage("Fail...", true)
                t.printStackTrace()
            }
        })
    }
}
