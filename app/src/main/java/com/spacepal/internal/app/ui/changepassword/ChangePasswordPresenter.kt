package com.spacepal.internal.app.ui.changepassword

import com.spacepal.internal.app.SpacePalApplication
import com.spacepal.internal.app.model.ChangePassword
import com.spacepal.internal.app.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordPresenter(private val changePasswordView: ChangePasswordContract.View) : ChangePasswordContract.Presenter {

    init{
        this.changePasswordView.setPresenter(this)
    }

    override fun changePasswordRequest(changePassword: ChangePassword) {
        changePasswordView.showProgressDialog(true)
        val call = SpacePalApplication.instance.api.changePassword(changePassword)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    changePasswordView.changePasswordRequestSuccess()
                    changePasswordView.showProgressDialog(false)
                } else {
                    val error = Util.parseError(response)
                    changePasswordView.showMessage(error.getErrorDescription()!!, true)
                    changePasswordView.showProgressDialog(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                changePasswordView.showProgressDialog(false)
                changePasswordView.showMessage("Fail...", true)
                t.printStackTrace()
            }

        })
    }

    override fun start() {
    }

}