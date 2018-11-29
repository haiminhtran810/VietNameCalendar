package home.learn.hmt.calendarvn_android.data

import home.learn.hmt.calendarvn_android.data.model.DayMonthYear
import java.util.*
import java.sql.Date
import java.text.SimpleDateFormat


fun INT(d: Double): Int {
    return Math.floor(d).toInt()
}

fun MOD(x: Int, y: Int): Int {
    var z = x - (y * Math.floor(x.toDouble() / y)).toInt()
    if (z == 0) {
        z = y
    }
    return z
}

// doi duong lich ra julius
fun universalToJD(dmy: DayMonthYear): Double {
    val JD: Double
    val D = dmy.day
    val M = dmy.month
    val Y = dmy.year

    if (Y > 1582 || Y == 1582 && M > 10
        || Y == 1582 && M == 10 && D > 14) {
        JD = ((367 * Y - INT((7 * (Y + INT(((M + 9) / 12).toDouble())) / 4).toDouble())
            - INT((3 * (INT(((Y + (M - 9) / 7) / 100).toDouble()) + 1) / 4).toDouble())).toDouble()
            + INT((275 * M / 9).toDouble()).toDouble() + D.toDouble() + 1721028.5)
    } else {
        JD = ((367 * Y - INT(
            (7 * (Y + 5001 + INT(((M - 9) / 7).toDouble())) / 4).toDouble())).toDouble()
            + INT((275 * M / 9).toDouble()).toDouble() + D.toDouble() + 1729776.5)
    }
    return JD
}

// doi julius ra duong lich
fun universalFromJD(JD: Double): DayMonthYear {
    val Z: Int
    val A: Int
    val alpha: Int
    val B: Int
    val C: Int
    val D: Int
    val E: Int
    val dd: Int
    val mm: Int
    val yyyy: Int
    val F: Double
    Z = INT(JD + 0.5)
    F = JD + 0.5 - Z
    if (Z < 2299161) {
        A = Z
    } else {
        alpha = INT((Z - 1867216.25) / 36524.25)
        A = Z + 1 + alpha - INT((alpha / 4).toDouble())
    }
    B = A + 1524
    C = INT((B - 122.1) / 365.25)
    D = INT(365.25 * C)
    E = INT((B - D) / 30.6001)
    dd = INT(B - D - INT(30.6001 * E) + F)
    if (E < 14) {
        mm = E - 1
    } else {
        mm = E - 13
    }
    if (mm < 3) {
        yyyy = C - 4715
    } else {
        yyyy = C - 4716
    }

    return DayMonthYear(dd, mm, yyyy)
}

// julius ra duong lich (theo gio Viet Nam)
fun localFromJD(JD: Double): DayMonthYear {
    return universalFromJD(JD + LOCAL_TIMEZONE / 24.0)
}

// duong lich ra julius (theo gio Viet Nam)
fun localToJD(dmy: DayMonthYear): Double {
    return universalToJD(dmy) - LOCAL_TIMEZONE / 24.0
}

// tinh thoi diem soc
fun newMoon(k: Int): Double {
    val T = k / 1236.85 // Time in Julian centuries from 1900 January
    // 0.5
    val T2 = T * T
    val T3 = T2 * T
    val dr = PI / 180
    var Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3
    Jd1 = Jd1 + 0.00033 * Math.sin((166.56 + 132.87 * T - 0.009173 * T2) * dr) // Mean
    // new
    // moon
    val M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3 // Sun's mean anomaly
    val Mpr = 306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3 // Moon's mean anomaly
    val F = 21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3 // Moon's argument of latitude
    var C1 = (0.1734 - 0.000393 * T) * Math.sin(M * dr) + 0.0021 * Math.sin(2.0 * dr * M)
    C1 = C1 - 0.4068 * Math.sin(Mpr * dr) + 0.0161 * Math.sin(dr * 2.0 * Mpr)
    C1 = C1 - 0.0004 * Math.sin(dr * 3.0 * Mpr)
    C1 = C1 + 0.0104 * Math.sin(dr * 2.0 * F) - 0.0051 * Math.sin(dr * (M + Mpr))
    C1 = C1 - 0.0074 * Math.sin(dr * (M - Mpr)) + 0.0004 * Math.sin(dr * (2 * F + M))
    C1 = C1 - 0.0004 * Math.sin(dr * (2 * F - M)) - 0.0006 * Math.sin(dr * (2 * F + Mpr))
    C1 = C1 + 0.0010 * Math.sin(dr * (2 * F - Mpr)) + 0.0005 * Math.sin(dr * (2 * Mpr + M))
    val deltat: Double
    if (T < -11) {
        deltat = (0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3
            - 0.000000081 * T * T3)
    } else {
        deltat = -0.000278 + 0.000265 * T + 0.000262 * T2
    }
    return Jd1 + C1 - deltat
}

// tinh vi tri mat troi luc 00:00 (dai diem tuy dau vao day julius)
fun sunLongitude(jdn: Double): Double {
    val T = (jdn - 2451545.0) / 36525 // Time in Julian centuries from
    // 2000-01-01 12:00:00 GMT
    val T2 = T * T
    val dr = PI / 180 // degree to radian
    val M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - (0.00000048
        * T * T2) // mean anomaly, degree
    val L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2 // mean
    // longitude,
    // degree
    var DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M)
    DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2.0 * M) + 0.000290 * Math.sin(dr * 3.0 * M)
    var L = L0 + DL // true longitude, degree
    L = L * dr
    L = L - PI * 2.0 * INT(L / (PI * 2)).toDouble() // Normalize to (0, 2*PI)
    return L
}

fun lunarMonth11(Y: Int): DayMonthYear {
    var dmy = DayMonthYear(31, 12, Y)
    val off = localToJD(dmy) - 2415021.076998695
    val k = INT(off / 29.530588853)
    var jd = newMoon(k)
    dmy = localFromJD(jd)
    val sunLong = sunLongitude(localToJD(dmy)) // sun
    // longitude
    // at
    // local
    // midnight
    if (sunLong > 3 * PI / 2) {
        jd = newMoon(k - 1)
    }
    return localFromJD(jd)
}

fun lunarYear(Y: Int): Array<DayMonthYear?> {
    var ret: Array<DayMonthYear?>
    val month11A = lunarMonth11(Y - 1)
    val jdMonth11A = localToJD(month11A)
    val k = Math
        .floor(0.5 + (jdMonth11A - 2415021.076998695) / 29.530588853).toInt()
    val month11B = lunarMonth11(Y)
    val off = localToJD(month11B) - jdMonth11A
    val leap = off > 365.0
    if (!leap) {
        ret = arrayOfNulls<DayMonthYear>(size = 13)
    } else {
        ret = arrayOfNulls<DayMonthYear>(size = 14)
    }
    ret[0] = month11A
    ret[ret.size - 1] = month11B

    for (i in 1 until ret.size - 1) {
        val nm = newMoon(k + i)
        ret[i] = localFromJD(nm)
        ret[i - 1]?.full = (daysBetween2Dates(ret[i - 1], ret[i]).toInt())
    }

    for (i in ret.indices) {
        ret[i]?.nm = MOD(i + 11, 12)
    }
    if (leap) {
        initLeapYear(ret)
    }
    return ret
}

internal fun initLeapYear(
    ret: Array<DayMonthYear?>) {
    val sunLongitudes = DoubleArray(ret.size)
    for (i in ret.indices) {
        val dmy = ret[i]
        val jdAtMonthBegin = localToJD(dmy!!)
        sunLongitudes[i] = sunLongitude(jdAtMonthBegin)
    }
    var found = false
    for (i in ret.indices) {
        if (found) {
            ret[i]?.nm = MOD(i + 10, 12)
            continue
        }
        val sl1 = sunLongitudes[i]
        val sl2 = sunLongitudes[i + 1]
        val hasMajorTerm = Math.floor(sl1 / PI * 6) != Math.floor(sl2 / PI * 6)
        if (!hasMajorTerm) {
            found = true
            ret[i]?.leap = 1
            ret[i]?.nm = MOD(i + 10, 12)
        }
    }
}


/* bat dau chuyen doi ngay am duong */
fun solar2Lunar(dmy: DayMonthYear): DayMonthYear {
    var yy = dmy.year
    val Y = yy
    var ly = lunarYear(Y) // Please cache the result of this
    // computation for later use!!!
    val month11 = ly[ly.size - 1]
    val jdToday = localToJD(dmy)
    val jdMonth11 = localToJD(month11!!)
    if (jdToday >= jdMonth11) {
        ly = lunarYear(Y + 1)
        yy = Y + 1
    }
    var i = ly.size - 1
    while (jdToday < localToJD(ly[i]!!)) {
        i--
    }
    val dd = (jdToday - localToJD(ly[i]!!)).toInt() + 1
    val mm = ly[i]?.nm
    if (mm!! >= 11) {
        yy--
    }

    val ret = DayMonthYear(dd, mm, yy, ly[i]!!.leap)
    ret.full = ly[i]!!.full

    return ret
}

// dmy: ngay, thang, nam, leap
fun lunar2Solar(dmy: DayMonthYear): DayMonthYear {
    var yy = dmy.year
    if (dmy.month >= 11) {
        yy = dmy.year + 1
    }
    val lunarYear = lunarYear(yy)
    var lunarMonth: DayMonthYear? = null
    for (i in lunarYear.indices) {
        val lm = lunarYear[i]
        if (lm?.nm === dmy.month && lm?.leap === dmy.leap) {
            lunarMonth = lm
            break
        }
    }
    if (lunarMonth != null) {
        val jd = localToJD(lunarMonth)
        return localFromJD(jd + dmy.day - 1)
    } else {
        throw RuntimeException("Incorrect input!")
    }
}


/* ket thuc chuyen doi ngay am duong */

/* bat dau can chi */

fun can(dmy: DayMonthYear): IntArray {
    val lunar = solar2Lunar(dmy)
    val y = lunar.year

    // return can ngay - thang - nam
    return intArrayOf(INT(localToJD(dmy) + 10.5) % 10, (y * 12 + lunar.month + 3) % 10,
        (y + 6) % 10)
}

fun chi(dmy: DayMonthYear): IntArray {
    val lunar = solar2Lunar(dmy)

    // return chi ngay - thang - nam
    return intArrayOf(INT(localToJD(dmy) + 2.5) % 12, (lunar.month + 1) % 12,
        (lunar.year + 8) % 12)
}

fun thu(dmy: DayMonthYear): Int {
    return (localToJD(dmy) + 1.5).toInt() % 7
}


/* ket thuc can chi */

fun maxDayOfMonth(m: Int, y: Int): Int {
    if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10
        || m == 12)
        return 31
    if (m == 4 || m == 6 || m == 9 || m == 11)
        return 30
    return if (m == 2) {
        if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
            29
        } else
            28
    } else 0
}

fun addDay(dmy: DayMonthYear, add: Int): DayMonthYear {
    var D = dmy.day
    var M = dmy.month
    var Y = dmy.year

    D += add
    do {
        if (D > maxDayOfMonth(M, Y)) {
            D -= maxDayOfMonth(M, Y)
            M++
            if (M > 12) {
                M = 1
                Y++
            }
        }

        if (D <= 0) {
            M--
            if (M < 1) {
                M = 12
                Y--
                D += 31
            } else {
                D += maxDayOfMonth(M, Y)
            }
        }
    } while (D < 0 || D > maxDayOfMonth(M, Y))

    return DayMonthYear(D, M, Y, 0, 0)
}

fun tietKhiMoc(Y: Int): Array<DayMonthYear?> {
    val ret = arrayOfNulls<DayMonthYear>(25)

    var i = 0
    var dmy = DayMonthYear(5, 1, Y, 0, 0)

    while (i < 24) {

        if (i == 5)
            while (sunLongitude(localToJD(dmy)) > SUNLONG_MAJOR[6]) {
                dmy = addDay(dmy, 1)
            }
        else
            while (sunLongitude(localToJD(dmy)) < SUNLONG_MAJOR[i]) {
                dmy = addDay(dmy, 1)
            }

        ret[i] = addDay(dmy, -1)

        dmy = addDay(dmy, 14)
        i++
    }

    ret[i] = DayMonthYear(31, 12, Y)

    return ret
}

fun tietKhi(dmy: DayMonthYear): Int {
    val moc = tietKhiMoc(dmy.year)
    var a: Long
    var b: Long

    if (dmy.day === 31 && dmy.month === 12)
        return 23
    else {
        for (i in 0..23) {
            a = daysBetween2Dates(moc[i], dmy)
            b = daysBetween2Dates(moc[i + 1], dmy)
            if (a < 0) {
                return 23
            } else {
                if (b < 0) {
                    return i
                }
            }
        }
    }

    return -1
}

fun truc(dmy: DayMonthYear): Int {

    var i = 0
    while (i < 24) {
        if (daysBetween2Dates(dmy, tietKhiMoc(dmy.year)[i]) > 0) {
            return (chi(dmy)[0] + 12 - i / 2) % 12
        }
        i += 2
    }

    return chi(dmy)[0]
}


fun daysBetween2Dates(
    dmy1: DayMonthYear?, dmy2: DayMonthYear?): Long {

    var c1 = Calendar.getInstance()
    var c2 = Calendar.getInstance()

    // Định nghĩa 2 mốc thời gian ban đầu
    val year1 = dmy1?.year
    val month1 = dmy1?.month
    val day1 = dmy2?.day
    val year2 = dmy2?.year
    val month2 = dmy2?.month
    val day2 = dmy2?.day

    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var date1 = sdf.parse("$year1-$month1-$day1")
    var date2 = sdf.parse("$year2-$month2-$day2")

    c1.time = date1
    c2.time = date2

    // Công thức tính số ngày giữa 2 mốc thời gian:

    return (c2.time.time - c1.time.time) / (24 * 3600 * 1000)
}

fun nhiThapBatTu(dmy: DayMonthYear): Int {

    return daysBetween2Dates(DayMonthYear(1, 1, 1975), dmy).toInt() % 28

}

// result= 0: ngay hac dao
// result= 1: ngay hoang dao
fun ngayHoangDao(dmy: DayMonthYear): Int {

    val hoangDao = intArrayOf(1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0)

    return hoangDao[(chi(dmy)[0] - (solar2Lunar(dmy).month * 2 - 2) % 12 + 12) % 12]

}

fun gioHoangDao(dmy: DayMonthYear): IntArray? {
    val chi = chi(dmy)[0]
    val gio: IntArray?

    when (chi) {
        0, 6 -> gio = intArrayOf(0, 1, 3, 6, 8, 9)

        1, 7 -> gio = intArrayOf(2, 3, 5, 8, 10, 11)

        2, 8 -> gio = intArrayOf(0, 1, 4, 5, 7, 10)

        3, 9 -> gio = intArrayOf(0, 2, 3, 6, 7, 9)

        4, 10 -> gio = intArrayOf(2, 4, 5, 8, 9, 11)

        5, 11 -> gio = intArrayOf(1, 4, 6, 7, 10, 11)

        else -> gio = null
    }

    return gio
}

fun saoTot(dmy: DayMonthYear): ArrayList<Int> {
    val ret = ArrayList<Int>()
    val sao = arrayOf(intArrayOf(5, 7, 9, 11, 1, 3, 5, 7, 9, 11, 1, 3),
        intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0),
        intArrayOf(6, 8, 10, 0, 2, 4, 6, 8, 10, 0, 2, 4),
        intArrayOf(10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
        intArrayOf(2, 8, 3, 9, 4, 10, 5, 11, 6, 0, 7, 1),
        intArrayOf(6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5),
        intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
        intArrayOf(7, 9, 11, 1, 3, 5, 7, 9, 7, 1, 3, 5),
        intArrayOf(10, 0, 2, 4, 6, 8, 10, 0, 2, 4, 6, 8),
        intArrayOf(6, 8, 10, 0, 2, 4, 6, 8, 10, 0, 2, 4),
        intArrayOf(9, 3, 10, 4, 11, 5, 0, 6, 1, 7, 2, 8),
        intArrayOf(8, 8, 10, 10, 0, 0, 2, 2, 4, 4, 6, 6),
        intArrayOf(10, 1, 2, 5, 9, 3, 0, 6, 8, 4, 8, 7))

    val m = solar2Lunar(dmy).month
    val chi = chi(dmy)[0]

    for (i in 0..12) {
        if (chi == sao[i][m - 1]) {
            ret.add(i)
        }
    }

    return ret
}

fun saoXau(dmy: DayMonthYear): ArrayList<Int> {
    val ret = ArrayList<Int>()
    val saoChi = arrayOf(intArrayOf(5, 0, 7, 2, 9, 4, 11, 6, 1, 8, 3, 10),
        intArrayOf(10, 4, 11, 5, 0, 6, 1, 7, 2, 8, 3, 9),
        intArrayOf(6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5),
        intArrayOf(5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4),
        intArrayOf(0, 5, 7, 3, 8, 10, 1, 11, 6, 9, 2, 4),
        intArrayOf(0, 3, 6, 9, 0, 3, 6, 9, 0, 3, 6, 9),
        intArrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 11),
        intArrayOf(1, 7, 2, 8, 3, 9, 4, 10, 5, 11, 0, 6),
        intArrayOf(8, 10, 10, 11, 1, 1, 2, 4, 4, 5, 7, 7),
        intArrayOf(5, 0, 1, 8, 3, 10, 11, 6, 7, 2, 9, 4),
        intArrayOf(11, 11, 11, 2, 2, 2, 5, 5, 5, 8, 8, 8),
        intArrayOf(2, 5, 8, 11, 3, 6, 9, 0, 4, 7, 10, 1),
        intArrayOf(10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
        intArrayOf(4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3))
    val saoCan = arrayOf(intArrayOf(0, 1, 4, 2, 3, 5, 6, 7, 5, 8, 9, 4),
        intArrayOf(6, 7, 5, 8, 9, 4, 0, 1, 5, 2, 3, 4))

    val m = solar2Lunar(dmy).month

    val chi = chi(dmy)[0]
    for (i in 0..13) {
        if (chi == saoChi[i][m - 1]) {
            ret.add(i)
        }
    }

    val can = can(dmy)[0]
    for (i in 0..1) {
        if (can == saoCan[i][m - 1]) {
            ret.add(i)
        }
    }

    return ret
}

fun arrayBatTuong(counter: Int): Array<IntArray>? {
    var ret: Array<IntArray>? = null

    when (counter) {
        0 -> ret = arrayOf(intArrayOf(2, 2), intArrayOf(3, 3), intArrayOf(4, 4), intArrayOf(3, 1),
            intArrayOf(4, 2), intArrayOf(5, 3), intArrayOf(6, 4), intArrayOf(6, 2),
            intArrayOf(7, 3), intArrayOf(7, 1), intArrayOf(2, 4))
        1 -> ret = arrayOf(intArrayOf(2, 2), intArrayOf(3, 3), intArrayOf(2, 5), intArrayOf(5, 3),
            intArrayOf(4, 0), intArrayOf(6, 2), intArrayOf(7, 3))
        2 -> ret = arrayOf(intArrayOf(1, 1), intArrayOf(2, 0), intArrayOf(3, 1), intArrayOf(2, 10),
            intArrayOf(4, 0), intArrayOf(5, 1), intArrayOf(4, 10), intArrayOf(6, 0),
            intArrayOf(6, 10))
        3 -> ret = arrayOf(intArrayOf(1, 1), intArrayOf(3, 1), intArrayOf(1, 9), intArrayOf(5, 1),
            intArrayOf(3, 9), intArrayOf(5, 9))
        4 -> ret = arrayOf(intArrayOf(0, 0), intArrayOf(0, 10), intArrayOf(2, 0), intArrayOf(0, 8),
            intArrayOf(1, 9), intArrayOf(2, 10), intArrayOf(4, 0), intArrayOf(2, 8),
            intArrayOf(3, 9), intArrayOf(4, 10))
        5 -> ret = arrayOf(intArrayOf(9, 9), intArrayOf(0, 10), intArrayOf(9, 7), intArrayOf(0, 8),
            intArrayOf(1, 9), intArrayOf(2, 10), intArrayOf(1, 7), intArrayOf(2, 8),
            intArrayOf(4, 0), intArrayOf(5, 7))
        6 -> ret = arrayOf(intArrayOf(8, 8), intArrayOf(9, 9), intArrayOf(0, 10), intArrayOf(8, 6),
            intArrayOf(9, 7), intArrayOf(0, 8), intArrayOf(1, 9), intArrayOf(0, 6),
            intArrayOf(1, 7), intArrayOf(4, 10), intArrayOf(5, 7), intArrayOf(8, 10))
        7 -> ret = arrayOf(intArrayOf(5, 5), intArrayOf(8, 8), intArrayOf(9, 9), intArrayOf(8, 6),
            intArrayOf(9, 7), intArrayOf(0, 8), intArrayOf(1, 9), intArrayOf(9, 5),
            intArrayOf(0, 6), intArrayOf(1, 7), intArrayOf(1, 5), intArrayOf(4, 6),
            intArrayOf(5, 7))
        8 -> ret = arrayOf(intArrayOf(4, 4), intArrayOf(5, 5), intArrayOf(7, 7), intArrayOf(7, 5),
            intArrayOf(8, 6), intArrayOf(9, 7), intArrayOf(9, 5), intArrayOf(9, 3),
            intArrayOf(4, 6), intArrayOf(5, 7))
        9 -> ret = arrayOf(intArrayOf(5, 5), intArrayOf(6, 6), intArrayOf(7, 7), intArrayOf(7, 5),
            intArrayOf(8, 6), intArrayOf(9, 7), intArrayOf(9, 5), intArrayOf(9, 3),
            intArrayOf(4, 6), intArrayOf(5, 7))
        10 -> ret = arrayOf(intArrayOf(4, 4), intArrayOf(6, 6), intArrayOf(4, 2), intArrayOf(5, 3),
            intArrayOf(6, 4), intArrayOf(8, 6), intArrayOf(6, 2), intArrayOf(7, 3),
            intArrayOf(8, 4), intArrayOf(8, 2), intArrayOf(9, 3), intArrayOf(4, 6))
        11 -> ret = arrayOf(intArrayOf(3, 3), intArrayOf(4, 4), intArrayOf(5, 5), intArrayOf(3, 1),
            intArrayOf(5, 3), intArrayOf(6, 4), intArrayOf(7, 5), intArrayOf(8, 4),
            intArrayOf(7, 1), intArrayOf(3, 5))
    }

    return ret
}

// lấy ra ngày bất tương trong khoảng cho trước
// lấy ra ngày bất tương trong khoảng cho trước
fun ngayBatTuong(dmy1: DayMonthYear,
    dmy2: DayMonthYear): ArrayList<DayMonthYear> {

    val ret = ArrayList<DayMonthYear>()
    var dmy = dmy1
    var arrBatTuong: Array<IntArray>
    var tietKhi: Int
    var can: Int
    var chi: Int
    var flag = true

    while (flag) {

        tietKhi = tietKhi(dmy)
        arrBatTuong = arrayBatTuong(tietKhi)!!
        can = can(dmy)[0]
        chi = chi(dmy)[0]

        for (i in arrBatTuong.indices) {
            if (can == arrBatTuong[i][0] && chi == arrBatTuong[i][1]) {
                ret.add(dmy)
                // dmy.printInfo();
            }
        }

        dmy = addDay(dmy, 1)

        if (daysBetween2Dates(dmy, dmy2) == 0L) {
            flag = false
            break
        }
    }
    return ret
}

// lấy ra 11 ngày tốt cho cưới xin trong khoảng cho trước
fun ngayTot(dmy1: DayMonthYear,
    dmy2: DayMonthYear): ArrayList<DayMonthYear> {

    val ret = ArrayList<DayMonthYear>()

    val arrNgayTot = arrayOf(intArrayOf(2, 2), intArrayOf(3, 3), intArrayOf(2, 5), intArrayOf(4, 2),
        intArrayOf(5, 3), intArrayOf(2, 10), intArrayOf(4, 0), intArrayOf(6, 2), intArrayOf(8, 2),
        intArrayOf(9, 3), intArrayOf(1, 5))

    var dmy = dmy1
    var can: Int
    var chi: Int
    var flag = true

    while (flag) {

        can = can(dmy)[0]
        chi = chi(dmy)[0]

        for (i in 0..10) {
            if (can == arrNgayTot[i][0] && chi == arrNgayTot[i][1]) {
                ret.add(dmy)
            }
        }

        dmy = addDay(dmy, 1)
        if (daysBetween2Dates(dmy, dmy2) == 0L) {
            flag = false
            break
        }
    }

    return ret
}

// lấy ra những ngày trực thành cho cưới xin trong khoảng cho trước
fun trucThanh(dmy1: DayMonthYear,
    dmy2: DayMonthYear): ArrayList<DayMonthYear> {
    val ret = ArrayList<DayMonthYear>()

    var dmy = dmy1
    var flag = true

    while (flag) {

        if (truc(dmy) == 8) {
            ret.add(dmy)
        }

        dmy = addDay(dmy, 1)

        if (daysBetween2Dates(dmy, dmy2) == 0L) {
            flag = false
            break
        }
    }

    return ret
}

// năm kỵ cưới gả tính theo năm sinh chú rể, cô dâu
fun namXau(dmyMen: DayMonthYear, dmyWoman: DayMonthYear): IntArray {
    val chiMen = chi(dmyMen)[2]
    val chiWoman = chi(dmyWoman)[2]
    val y = IntArray(2)

    y[0] = (chiMen + 7) % 12
    y[1] = (15 - chiWoman) % 12

    return y
}

// loại bỏ những ngày ngưu lang chức nữ, không sang, không phòng; nguyệt ky,
// tam nương, dương công kỵ nhật trong list result
fun ngayXau(result: ArrayList<DayMonthYear>) {

    var dmy = DayMonthYear()
    var mua: Int
    var chi: Int
    var d: Int
    var m: Int

    for (i in result.indices) {
        dmy = result[i]
        chi = chi(dmy)[0]
        mua = tietKhi(dmy)
        d = solar2Lunar(dmy).day
        m = solar2Lunar(dmy).month

        // 3 ngày nguyệt kỵ và 6 ngày tam nương
        if (d == 5 || d == 14 || d == 23 || d == 3 || d == 7 || d == 13
            || d == 18 || d == 22 || d == 27) {
            result.removeAt(i)
        }

        // 13 ngày dương công kỵ nhật (bỏ những ngày trùng với trên)
        if (d == 11 && m == 2 || d == 9 && m == 3 || d == 8 && m == 7
            || d == 29 && m == 7 || d == 25 && m == 9
            || d == 21 && m == 11 || d == 19 && m == 12) {
            result.removeAt(i)
        }

        // mùa đông: sửu, dần, thân, dậu
        if (mua < 2 || mua >= 20) {
            if (chi == 1 || chi == 2 || chi == 8 || chi == 9)
                result.removeAt(i)
        }

        // mùa xuân: tý, thìn, tỵ, dậu
        if (mua >= 2 && mua < 8) {
            if (chi == 0 || chi == 4 || chi == 5 || chi == 9)
                result.removeAt(i)
        }

        // mùa hè: sửu, mão, mùi, tuất
        if (mua >= 8 && mua < 14) {
            if (chi == 1 || chi == 3 || chi == 7 || chi == 10)
                result.removeAt(i)
        }

        // mùa thu: dần, mão, ngọ, thân, tuất
        if (mua >= 14 && mua < 20) {
            if (chi == 2 || chi == 3 || chi == 6 || chi == 8 || chi == 10)
                result.removeAt(i)
        }
    }
}

// chọn ngày cưới trong khoảng cho trước
fun cuoiGa(dmy1: DayMonthYear,
    dmy2: DayMonthYear, dmyMen: DayMonthYear, dmyWoman: DayMonthYear): ArrayList<DayMonthYear> {

    val ret = ngayBatTuong(dmy1, dmy2)
    val resultNgayTot = ngayTot(dmy1, dmy2)
    val resultTrucThanh = trucThanh(dmy1, dmy2)

    var counter = ret.size
    var k = 0
    var y = namXau(dmyMen, dmyWoman)

    for (i in resultNgayTot.indices) {
        for (j in 0 until counter) {
            if (resultNgayTot[i].day === ret.get(
                    j).day && resultNgayTot[i].month === ret.get(j)
                    .month) {
                k = 0
                break
            } else
                k++

        }

        if (k == counter) {
            ret.add(resultNgayTot[i])
        }

        k = 0
    }

    counter = ret.size
    k = 0
    for (i in resultTrucThanh.indices) {
        for (j in 0 until counter) {
            if (resultTrucThanh[i].day === ret.get(
                    j).day && resultTrucThanh[i].month === ret.get(j)
                    .month) {
                k = 0
                break
            } else
                k++

        }

        if (k == counter) {
            ret.add(resultTrucThanh[i])
        }

        k = 0
    }

    ngayXau(ret)
    coHuSat(ret, dmyMen, dmyWoman)
    sortResult(ret)

    y = namXau(dmyMen, dmyWoman)
    for (i in dmy1.year until dmy1.year + 11) {
        val chiNam = chi(DayMonthYear(5, 5, i))[2]
        if (chiNam == y[0] || chiNam == y[1]) {
            println("Nam: $i khong tot")
        }
    }
    return ret
}

// loại bỏ ngày xấu cô hư sát trong result (cho biết ngày tháng năm sinh chú
// rể, cô dâu)
fun coHuSat(result: ArrayList<DayMonthYear>,
    dmyMen: DayMonthYear, dmyWoman: DayMonthYear) {

    var month: Int

    val hu = IntArray(2)

    val co = (22 - (can(dmyMen)[2] - chi(dmyMen)[2] + 12) % 12) % 12
    val coHuSatWoman = (can(dmyWoman)[2] - chi(dmyWoman)[2] + 12) % 12

    when (coHuSatWoman) {
        0 -> {
            hu[0] = 2
            hu[1] = 4
        }
        2 -> {
            hu[0] = 1
            hu[1] = 3
        }
        4 -> {
            hu[0] = 11
            hu[1] = 12
        }
        6 -> {
            hu[0] = 9
            hu[1] = 10
        }
        8 -> {
            hu[0] = 7
            hu[1] = 8
        }
        10 -> {
            hu[0] = 5
            hu[1] = 6
        }
    }

    for (i in result.indices) {
        month = solar2Lunar(result[i]).month
        if (month == co || month == co - 1 || month == hu[0]
            || month == hu[1]) {
            result.removeAt(i)
        }
    }
}

// sắp xếp ngày tháng năm
fun sortResult(result: ArrayList<DayMonthYear>) {

    result.sortWith(Comparator { o1, o2 ->
        // TODO Auto-generated method stub
        val noDay = daysBetween2Dates(o1, o2)
        if (noDay > 0)
            -1
        else {
            if (noDay == 0L)
                0
            else
                1
        }
    })

}