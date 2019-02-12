package home.learn.hmt.calendarvn_android.widges

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.data.model.Calendardate
import kotlinx.android.synthetic.main.layout_calendar_day.view.*

class CalendarDayView(context: Context, val date: Calendardate) : LinearLayout(context) {
    private var mLayoutBackground: View? = null

    init {
        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.layout_calendar_day, this)
        date?.let {
            tv_calendar_day_text.text = it.mDay.toString()
        }
    }

    fun setThisMonthTextColor() {
        tv_calendar_day_text.setTextColor(ContextCompat.getColor(context, R.color.whiteFive))
    }

    fun setOtherMonthTextColor() {
        tv_calendar_day_text.setTextColor(ContextCompat.getColor(context, R.color.warmGrey))
    }

    fun setPurpleSolidOvalBackground() {
        tv_calendar_day_layout_background.setBackgroundResource(R.drawable.oval_purple_solid)
    }

    fun unsetPurpleSolidOvalBackground() {
        tv_calendar_day_layout_background.setBackgroundResource(R.drawable.oval_black_solid)
    }
}