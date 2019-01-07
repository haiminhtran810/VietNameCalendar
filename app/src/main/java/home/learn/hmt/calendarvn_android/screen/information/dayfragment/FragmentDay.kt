package home.learn.hmt.calendarvn_android.screen.information.dayfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment
import home.learn.hmt.calendarvn_android.screen.information.InformationFragment
import kotlinx.android.synthetic.main.day_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FragmentDay : BaseFragment() {
    companion object {
        const val TAG = "FragmentDay"
        fun newInstance(day: Int): FragmentDay {
            val fragmentDay = FragmentDay()
            val bundle = Bundle()
            bundle.putInt(TAG, day)
            fragmentDay.arguments = bundle
            return fragmentDay
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_fragment, container, false)
    }

    override fun initView() {
        super.initView()
        arguments?.apply {
            val day = this.getInt(TAG, 0)
            updateUI(day)
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onEvent(dayOfWeekData: InformationFragment.DayOfWeekData) {
        tv_day_of_week.text = dayOfWeekData.day
    }

    override fun handlers() {
        super.handlers()
    }

    override fun observe() {
        super.observe()
    }

    private fun updateUI(day: Int) {
        tv_date_information.text = day.toString()
    }


    interface IGetItem {
        fun maxDay(): Int
        fun maxDayPre(): Int
    }
}