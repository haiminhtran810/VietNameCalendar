package home.learn.hmt.calendarvn_android.calendar.interfaces

import android.content.Context
import android.graphics.Point
import android.view.WindowManager


fun getScreenWidth(context: Context): Int {
    return getPointSize(context).x
}

fun getScreenHeight(context: Context): Int {
    return getPointSize(context).y
}

private fun getPointSize(context: Context): Point {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

