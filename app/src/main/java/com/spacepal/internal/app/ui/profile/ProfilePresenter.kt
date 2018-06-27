package com.spacepal.internal.app.ui.profile

import android.util.Log
import com.spacepal.internal.app.Constant
import com.spacepal.internal.app.SpacePalApplication
import com.spacepal.internal.app.model.Profile
import com.spacepal.internal.app.util.PreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfilePresenter(private val profileView: ProfileContract.View, private var preferenceUtil: PreferenceUtil) : ProfileContract.Presenter, Constant {


    override fun submit(profile: Profile) {
        val call = SpacePalApplication.instance.api.updateAccount(profile)
        call.enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                if (response!!.code() == 200) {
                    Log.e("Alhumdulilah", response.code().toString())
                } else {
                    Log.e("something went wrong", response.code().toString())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                profileView.showProgressDialog(false)
                profileView.showMessage("Fail...", true)
                t.printStackTrace()
            }
        })
    }

    override fun getToken(username: String, password: String) {
    }

    override fun start() {
    }

    init {
        profileView.setPresenter(this)
    }

}