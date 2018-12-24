package home.learn.hmt.calendarvn_android.screen

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseActivity
import home.learn.hmt.calendarvn_android.screen.calendar.CalendarFragment
import home.learn.hmt.calendarvn_android.screen.information.InformationFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().add(R.id.container,
            InformationFragment.newInstance(), InformationFragment.TAG).commit()

    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.menu_calendar -> {
                supportFragmentManager.beginTransaction().add(R.id.container,
                    CalendarFragment.newInstance(), CalendarFragment.TAG).commit()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
