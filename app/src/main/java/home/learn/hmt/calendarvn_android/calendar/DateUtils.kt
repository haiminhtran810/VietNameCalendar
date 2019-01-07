package home.learn.hmt.calendarvn_android.calendar

import home.learn.hmt.calendarvn_android.data.MONTH
import home.learn.hmt.calendarvn_android.data.THU
import home.learn.hmt.calendarvn_android.data.YY_MM_DD
import home.learn.hmt.calendarvn_android.data.model.Calendardate
import home.learn.hmt.calendarvn_android.data.model.DayMonthYear
import java.text.SimpleDateFormat
import java.util.*

fun monthToString(month: Int): String = MONTH[month]
fun dayOfWeekToString(day: Int): String = THU[day]
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

fun getDayOfWeek(dmy: DayMonthYear): String {
    val dayFormat = dmy.year.toString() + ":" + dmy.month.toString() + ":" + dmy.day.toString()
    val simpleDateFormat = SimpleDateFormat(YY_MM_DD)
    val date = simpleDateFormat.parse(dayFormat)
    val day = date.day
    return dayOfWeekToString(day)
}
