package home.learn.hmt.calendarvn_android.widges

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.TextView
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.calendar.interfaces.OnDayViewClickListener
import home.learn.hmt.calendarvn_android.calendar.interfaces.getScreenWidth
import home.learn.hmt.calendarvn_android.calendar.isDateEqual
import home.learn.hmt.calendarvn_android.data.NUMBER_OF_DAYS_IN_WEEK
import home.learn.hmt.calendarvn_android.data.NUMBER_OF_WEEKS_IN_MONTH
import home.learn.hmt.calendarvn_android.data.model.CalendarMonth
import home.learn.hmt.calendarvn_android.data.model.Calendardate

class CalendarMonthView(context: Context) : FrameLayout(context), View.OnClickListener {

    private var mGridLayout: GridLayout? = null
    private var mLayoutDays: ViewGroup? = null
    private var mListener: OnDayViewClickListener? = null
    private var mSelectedDate: Calendardate? = null

    init {
        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.layout_calendar_month, this)
    }

    fun buildView(calendarMonth: CalendarMonth) {
        buildDaysLayout()
        buildGridView(calendarMonth)
    }

    private fun buildGridView(calendarMonth: CalendarMonth) {
        val row = NUMBER_OF_WEEKS_IN_MONTH
        val col = NUMBER_OF_DAYS_IN_WEEK
        mGridLayout!!.rowCount = row
        mGridLayout!!.columnCount = col
        val screenWidth = getScreenWidth(context)
        val width = screenWidth / col
        for (date in calendarMonth.mDays) {
            val params = GridLayout.LayoutParams()
            params.width = width
            params.height = FrameLayout.LayoutParams.WRAP_CONTENT
            val dayView = CalendarDayView(context, date)
            dayView.contentDescription = date.toString()
            dayView.layoutParams = params
            dayView.setOnClickListener(this)
            decorateDayView(dayView, date, calendarMonth.mMonth)
        }
    }

    private fun decorateDayView(dayView: CalendarDayView, day: Calendardate, month: Int) {
        if (day.mMonth != month) {
            dayView.setOtherMonthTextColor()
        } else {
            dayView.setThisMonthTextColor()
        }

        mSelectedDate?.let {
            if (isDateEqual(it, day)) {
                dayView.setPurpleSolidOvalBackground()
            } else {
                dayView.unsetPurpleSolidOvalBackground()
            }
        }
    }

    private fun buildDaysLayout() {
        val days: Array<String> = resources.getStringArray(R.array.days_sunday_array)
        for (i in 0..mLayoutDays!!.childCount) {
            val textView = mLayoutDays!!.getChildAt(i) as TextView
            textView.text = days[i]
        }
    }

    fun setOnDayViewClickListener(listener: OnDayViewClickListener) {
        mListener = listener
    }


    override fun onClick(p0: View?) {
        mListener?.let {
            it.onDayViewClick(p0 as CalendarDayView)
        }
    }
}