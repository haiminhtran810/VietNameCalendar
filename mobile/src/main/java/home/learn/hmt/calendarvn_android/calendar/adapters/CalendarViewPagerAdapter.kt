package home.learn.hmt.calendarvn_android.calendar.adapters

import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import home.learn.hmt.calendarvn_android.calendar.interfaces.OnDayViewClickListener
import home.learn.hmt.calendarvn_android.data.model.CalendarMonth
import home.learn.hmt.calendarvn_android.widges.CalendarDayView

class CalendarViewPagerAdapter(
    private val list: List<CalendarMonth>,
    private val viewPager: ViewPager) : PagerAdapter(), OnDayViewClickListener {
    override fun onDayViewClick(view: CalendarDayView) {
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}