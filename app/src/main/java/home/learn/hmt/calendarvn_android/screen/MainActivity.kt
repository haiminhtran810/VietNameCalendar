package home.learn.hmt.calendarvn_android.screen

import android.os.Bundle
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseActivity
import home.learn.hmt.calendarvn_android.screen.information.InformationFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.container, InformationFragment.newInstance(), InformationFragment.TAG).commit()
        nav_view.apply {
        }
    }
}
