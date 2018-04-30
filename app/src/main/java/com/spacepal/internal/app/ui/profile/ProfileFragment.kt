package com.spacepal.internal.app.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.spacepal.internal.app.BaseFragment
import com.spacepal.internal.app.R
import com.spacepal.internal.app.model.response.Profile
import kotlinx.android.synthetic.main.fragment_profile.*

public class ProfileFragment : BaseFragment(), ProfileContract.View {

    private lateinit var presenter: ProfileContract.Presenter

    override fun initUI(view: View) {
        buttonSubmit.setOnClickListener { sumbitForm() }
    }

    override fun submitSuccessfull(profile: Profile) {

    }
//    {"id":"25c18bbe-c65f-47b2-accd-6b9bf29e5618","firstName":"PickerDriver","lastName":"Roadhouse","email":"pickerdriver@roadhouse.com.au","isDisabled":false,"roles":["Picker","Driver"],"phoneNumber":null,"isArchived":false,"imageUrl":null,"customerType":null,"fileUrls":[],"notes":[],"addresses":[]}
    fun sumbitForm() {
        showAlert("yes!", true)
        presenter.submit(Profile(
                null, "pickerdriver2", "Roadhouse", "", "", null, "6806090"
                , null, null, null, null, "pickerdriver@roadhouse.com.au", true)
        )
    }

    override fun showProgressDialog(isInProgress: Boolean) {
        if (isInProgress)
            showProgress()
        else
            hideProgress()
    }

    override fun setPresenter(presenter: ProfileContract.Presenter) {
        this.presenter = presenter
    }

    override val id: Int
        get() = R.layout.fragment_profile


    companion object {

        fun newInstance(): ProfileFragment {
            val args = Bundle()
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }


}