package com.spacepal.internal.app

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadhourse.spacepal.ui.login.LoginActivity
import org.jetbrains.anko.alert

abstract class BaseFragment : Fragment(), Constant {
    var mBaseActivity: BaseActivity? = null
    private var view: View? = null

    internal abstract val id: Int


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity)
            mBaseActivity = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(id, container, false)
        this.view = view
        initUI(view)
        return view
    }

    abstract fun initUI(view: View)

    override fun getView(): View? {
        return this.view
    }

    fun showProgress() {
        mBaseActivity!!.showProgress()
    }

    fun hideProgress() {
        mBaseActivity!!.hideProgress()
    }


    fun showAlert(message: String, alert: Boolean) {
        try {
            mBaseActivity!!.showAlertDialog(message, alert, LoginActivity())
        } catch (e: Exception) {
            Log.e("Dialog Exception", e.message)
        }

    }

    fun alertMessages(message: String) {
        mBaseActivity!!.alert(message) {
            mBaseActivity!!.title = "Alert"
            yesButton { }
        }.show()
    }


}
