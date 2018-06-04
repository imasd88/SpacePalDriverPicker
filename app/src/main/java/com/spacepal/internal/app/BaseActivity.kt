package com.spacepal.internal.app;

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.spacepal.internal.app.ui.ProgressFragmentDialog
import org.jetbrains.anko.alert


/**
 * Created by sidhu on 4/24/2018.
 */

abstract class BaseActivity : AppCompatActivity(), Constant {

    abstract val id: Int

    private var pd: ProgressFragmentDialog? = null

    abstract fun created(savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(id)
        created(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()

    }


    fun setToolBar(toolbar: Toolbar, title: CharSequence, showToolbar: Boolean) {
        setSupportActionBar(toolbar)
        showToolbar(showToolbar)
    }


    fun showToolbar(show: Boolean) {
        if (supportActionBar != null) {
            if (show)
                supportActionBar!!.show()
            else
                supportActionBar!!.hide()
        }
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs Camera permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()

    }

    // navigating user to app settings
    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    /**** show progress  */

    fun showProgress() {

        if (pd == null) {
            pd = ProgressFragmentDialog.newInstance()
        }
        pd!!.show(supportFragmentManager, "TAG")

    }

    /*
            ****************** hide progress **********************
     */

    fun hideProgress() {
        if (pd != null) {
            pd!!.dismiss()
        }

    }

    fun showAlertDialog(message: String, alert: Boolean, activity: BaseActivity) {
        alert(message) {
            title = "Alert"
            yesButton { }
        }.show()

        // Create Alert using Builder
        /*    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT).setCornerRadius(50)
                .setTitle(title)
                .setMessage(message+"\n")
                .setCancelable(true)
                .setAutoDismissAfter(4000)
                .addButton("     OK     ", -1, ContextCompat.getColor(this,R.color.blue),
                        CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, (dialog, which) -> {
                            dialog.dismiss();
                        });

        builder.show();*/
    }

}
