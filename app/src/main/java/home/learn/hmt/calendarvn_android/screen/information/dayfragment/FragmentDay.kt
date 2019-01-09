package home.learn.hmt.calendarvn_android.screen.information.dayfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment
import home.learn.hmt.calendarvn_android.calendar.getDayOfWeek
import home.learn.hmt.calendarvn_android.data.folks
import home.learn.hmt.calendarvn_android.data.model.DayMonthYear
import home.learn.hmt.calendarvn_android.screen.information.InformationFragment
import kotlinx.android.synthetic.main.day_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class FragmentDay : BaseFragment() {
    companion object {
        const val TAG = "FragmentDay"
        const val TAG_DMY = "FragmentDayDMY"
        const val NMF = 20
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

    fun updateUI(day: Int, dayOfWeek: String) {
        tv_date_information.text = day.toString()
        tv_day_of_week.text = dayOfWeek
        txt_fox.text = folks[Random().nextInt(NMF)]
    }


    interface IGetItem {
        fun maxDay(): Int
        fun maxDayPre(): Int
    }
}