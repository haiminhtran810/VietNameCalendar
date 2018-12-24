package home.learn.hmt.calendarvn_android.screen.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment

class CalendarFragment : BaseFragment(){

    companion object {
        const val TAG = "CalendarFragment"
        fun newInstance() = CalendarFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun initView() {
        super.initView()
    }

    override fun handlers() {
        super.handlers()
    }

    override fun observe() {
        super.observe()
    }


}