package com.spacepal.internal.app.ui.dashboard

import com.spacepal.internal.app.BasePresenter
import com.spacepal.internal.app.BaseView
import com.spacepal.internal.app.model.response.AssignmentItem


class OrderListContract {
     interface View : BaseView<Presenter> {
        fun showOrders(mListAssignmentItem: List<AssignmentItem>)
        fun showMessage(text: String, alert: Boolean)
        fun showProgressDialog(isInProgress: Boolean)
        fun showOnErrorOnEmpty()
    }


     interface Presenter : BasePresenter {
        fun getOrders(userId:String, role: String)
    }
}
