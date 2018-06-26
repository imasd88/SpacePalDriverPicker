package com.spacepal.internal.app.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.roadhourse.spacepal.ui.dashboard.OrderListFragment
import com.spacepal.internal.app.BaseActivity
import com.spacepal.internal.app.R
import com.spacepal.internal.app.ui.profile.ProfileActivity
import com.spacepal.internal.app.util.PreferenceUtil
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardActivity : BaseActivity() {
    override val id: Int
        get() = R.layout.activity_dashboard

    override fun created(savedInstanceState: Bundle?) {


        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        sliding_tabs!!.setupWithViewPager(viewPager)

        val adapter = DashboardFragmentPagerAdapter(this, supportFragmentManager, PreferenceUtil.getInstance(this).account)
        val profile = PreferenceUtil.getInstance(this).account


        val orderListFragments = java.util.ArrayList<OrderListFragment>()
        for (i in 0 until profile.roles!!.size) {
            orderListFragments.add(OrderListFragment.getInstance(profile.roles!!.get(i)!!, profile.id!!))
            OrderListPresenter(orderListFragments[i], PreferenceUtil.getInstance(this))
            adapter.addFragment(orderListFragments[i])
        }

        // Set the adapter onto the view pager
        viewPager!!.adapter = adapter

    }


//    override fun onSupportNavigateUp(): Boolean {
//        this.finish()
//        return true
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
