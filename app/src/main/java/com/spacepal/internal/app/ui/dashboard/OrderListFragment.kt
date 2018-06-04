package com.roadhourse.spacepal.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.spacepal.internal.app.BaseFragment
import com.spacepal.internal.app.R
import com.spacepal.internal.app.model.adapter.OrderAdapter
import com.spacepal.internal.app.model.response.AssignmentItem
import com.spacepal.internal.app.ui.dashboard.OrderListContract
import java_version.job.JobActivity
import kotlinx.android.synthetic.main.fragment_jobs_list.*


class OrderListFragment : BaseFragment(), OrderListContract.View, OrderAdapter.OnItemClickListener {


    override fun showOnErrorOnEmpty() {
        jobsRecyclerView.visibility = View.GONE
        tvMessage.visibility = View.VISIBLE
    }

    private lateinit var presenter: OrderListContract.Presenter
    private var mListAssignmentItem: List<AssignmentItem>? =  ArrayList()

    override val id: Int
        get() = R.layout.fragment_jobs_list

    override fun initUI(view: View) {
        val role = arguments!!.getString(ARG_ROLE, "")
        val userId = arguments!!.getString(ARG_USER_ID)
        presenter.getOrders(userId,role)
    }

    override fun setPresenter(presenter: OrderListContract.Presenter) {
        this.presenter = presenter
    }

    override fun showOrders(mListAssignmentItem: List<AssignmentItem>) {
        this.mListAssignmentItem=mListAssignmentItem
        jobsRecyclerView.layoutManager = LinearLayoutManager(mBaseActivity)
        jobsRecyclerView.itemAnimator = DefaultItemAnimator()
        jobsRecyclerView.adapter =  OrderAdapter(mBaseActivity,mListAssignmentItem,this)
    }

    override fun showMessage(text: String, alert: Boolean) {
        Toast.makeText(mBaseActivity, text, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        var i = Intent()
        i.setClass(activity, JobActivity::class.java)
        i.putExtra("Assignment", mListAssignmentItem!![position])
        startActivity(i)

    }

    override fun showProgressDialog(isInProgress: Boolean) {
        if (isInProgress)
            showProgress()
        else
            hideProgress()
    }

    companion object {

        var ARG_ROLE = "role"
        var ARG_USER_ID = "user_id"
        fun getInstance(role: String, userId: String): OrderListFragment {
            val args = Bundle()
            args.putString(ARG_ROLE, role)
            args.putString(ARG_USER_ID, userId)
            val orderListFragment = OrderListFragment()
            orderListFragment.arguments = args
            return orderListFragment
        }
    }
}
