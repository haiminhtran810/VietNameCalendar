package home.learn.hmt.calendarvn_android.screen.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment
import home.learn.hmt.calendarvn_android.calendar.gone
import kotlinx.android.synthetic.main.calendar_fragment.*
import kotlinx.android.synthetic.main.layout_header.*
import kotlinx.android.synthetic.main.layout_header.view.*

class CalendarFragment : BaseFragment() {

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
        layout_header?.apply {
            img_navigation.setImageResource(R.drawable.ic_arrow_back_black_24dp)
            lg_header_month.gone()
        }
    }

    override fun handlers() {
        super.handlers()
        img_navigation.setOnClickListener {
            onBackProcess()
        }
    }

    override fun observe() {
        super.observe()
    }

    fun onBackProcess() {
        fragmentManager?.popBackStack()
    }

}