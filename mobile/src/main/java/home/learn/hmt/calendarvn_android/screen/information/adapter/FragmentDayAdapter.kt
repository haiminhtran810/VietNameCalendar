package home.learn.hmt.calendarvn_android.screen.information.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import home.learn.hmt.calendarvn_android.data.maxDayOfMonth
import home.learn.hmt.calendarvn_android.data.model.DayMonthYear
import home.learn.hmt.calendarvn_android.screen.information.dayfragment.FragmentDay

class FragmentDayAdapter(
    private val fm: FragmentManager,
    private val dmy: DayMonthYear
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return FragmentDay.newInstance(position, dmy)
    }

    override fun getCount(): Int {
        return (maxDayOfMonth(dmy.month, dmy.year) + 2)
    }
}