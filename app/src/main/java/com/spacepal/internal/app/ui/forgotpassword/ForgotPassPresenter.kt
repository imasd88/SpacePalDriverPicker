package com.spacepal.internal.app.ui.forgotpassword

import android.util.Log
import com.spacepal.internal.app.SpacePalApplication
import com.spacepal.internal.app.model.EmailBody
import com.spacepal.internal.app.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


public class ForgotPassPresenter(private val forgotPassView: ForgotPassContract.View) : ForgotPassContract.Presenter {
    override fun forgotPassRequest(email: String) {
        forgotPassView.showProgressDialog(true)
        val call = SpacePalApplication.instance.api.forgotPass(EmailBody(email))
        call.enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    forgotPassView.forgotPassRequestSuccess()
                    forgotPassView.showProgressDialog(false)
                } else {

                    val error = Util.parseError(response)
                    forgotPassView.showMessage(error.getErrorDescription()!!, true)
                    forgotPassView.showProgressDialog(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("onFailure", t.message)
                forgotPassView.showProgressDialog(false)
                forgotPassView.showMessage("Fail...", true)
                t.printStackTrace()
            }
        })
    }

    override fun start() {
    }

    init {
        forgotPassView.setPresenter(this)
    }

}