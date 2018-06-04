package com.spacepal.internal.app.ui.dashboard

import com.spacepal.internal.app.model.response.AssignmentResponse
import com.spacepal.internal.app.source.RetrofitHelper
import com.spacepal.internal.app.util.PreferenceUtil
import com.spacepal.internal.app.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListPresenter(private val view: OrderListContract.View, private val preferenceUtil: PreferenceUtil) : OrderListContract.Presenter {

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun getOrders(userId: String,role: String) {
        view.showProgressDialog(false)
        val call = RetrofitHelper.instance!!.api!!.getOrders(userId,role)
        call.enqueue(object : Callback<AssignmentResponse> {
            override fun onResponse(call: Call<AssignmentResponse>, response: Response<AssignmentResponse>) {
                if (response.code() == 200) {
                    view.showOrders(response.body()?.items!!)
                } else {
                    val error = Util.parseError(response)
                    view.showMessage(error.getError()!!, true)
                    view.showOnErrorOnEmpty()
                }
                view.showProgressDialog(false)
            }

            override fun onFailure(call: Call<AssignmentResponse>, t: Throwable) {
                view.showProgressDialog(false)
                view.showMessage("Fail...", true)
                t.printStackTrace()
            }
        })
    }
}
