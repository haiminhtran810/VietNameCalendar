package home.learn.hmt.calendarvn_android.calendar

import home.learn.hmt.calendarvn_android.data.MONTH
import home.learn.hmt.calendarvn_android.data.THU
import home.learn.hmt.calendarvn_android.data.model.Calendardate
import java.util.*

fun monthToString(month: Int): String = MONTH[month]
fun dayOfWeekToString(month: Int): String = THU[month]
fun isToday(mYear: Int, mMonth: Int, mDate: Int): Boolean {
    val today = Calendar.getInstance()
    return mYear == today.get(Calendar.YEAR) &&
        mMonth == today.get(Calendar.MONTH) &&
        mDate == today.get(Calendar.DAY_OF_MONTH)
}

fun isDateEqual(date1: Calendardate, date2: Calendardate): Boolean {
    return date2.mYear == date2.mYear &&
        date2.mMonth == date2.mMonth &&
        date2.mDay == date2.mDay
}
