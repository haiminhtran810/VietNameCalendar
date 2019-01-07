package home.learn.hmt.calendarvn_android.screen.information.dayfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment
import home.learn.hmt.calendarvn_android.calendar.getDayOfWeek
import home.learn.hmt.calendarvn_android.data.model.DayMonthYear
import kotlinx.android.synthetic.main.day_fragment.*

class FragmentDay : BaseFragment() {
    companion object {
        const val TAG = "FragmentDay"
        const val TAG_DMY = "FragmentDayDMY"
        fun newInstance(day: Int, dmy: DayMonthYear): FragmentDay {
            val fragmentDay = FragmentDay()
            val dayOfWeek = getDayOfWeek(dmy)
            val bundle = Bundle()
            bundle.putInt(TAG, day)
            bundle.putString(TAG_DMY, dayOfWeek)
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
            val dayOfWeek = this.getString(TAG_DMY)
            updateUI(day, dayOfWeek)
        }

    }

    override fun handlers() {
        super.handlers()
    }

    override fun observe() {
        super.observe()
    }

    fun updateUI(day: Int, dayOfWeek: String) {
        tv_date_information.text = day.toString()
        tv_day_of_week.text = dayOfWeek
    }

    interface IGetItem {
        fun maxDay(): Int
        fun maxDayPre(): Int
    }
}