package home.learn.hmt.calendarvn_android.data

import java.util.*
import java.sql.Date


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
fun universalToJD(D: Int, M: Int, Y: Int): Double {
    val JD: Double
    if (Y > 1582 || Y == 1582 && M > 10
        || Y == 1582 && M == 10 && D > 14) {
        JD = ((367 * Y - (7 * (Y + ((M + 9) / 12).toInt()) / 4).toInt()
            - (3 * (((Y + (M - 9) / 7) / 100).toInt() + 1) / 4).toInt())
            + (275 * M / 9).toInt() + D + 1721028.5)
    } else {
        JD = (367 * Y - (7 * (Y + 5001 + ((M - 9) / 7).toInt()) / 4).toInt()
            + (275 * M / 9).toInt() + D + 1729776.5)
    }
    return JD
}

// doi julius ra duong lich
fun universalFromJD(JD: Double): IntArray {
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
        alpha = ((Z - 1867216.25) / 36524.25).toInt()
        A = Z + 1 + alpha - (alpha / 4).toInt()
    }
    B = A + 1524
    C = ((B - 122.1) / 365.25).toInt()
    D = (365.25 * C).toInt()
    E = ((B - D) / 30.6001).toInt()
    dd = (B - D - (30.6001 * E).toInt() + F).toInt()
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
    return intArrayOf(dd, mm, yyyy)
}

// julius ra duong lich (theo gio Viet Nam)
fun localFromJD(JD: Double): IntArray {
    return universalFromJD(JD + LOCAL_TIMEZONE / 24.0)
}

// duong lich ra julius (theo gio Viet Nam)
fun localToJD(D: Int, M: Int, Y: Int): Double {
    return universalToJD(D, M, Y) - LOCAL_TIMEZONE / 24.0
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
    val delta: Double
    if (T < -11) {
        delta = (0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3
            - 0.000000081 * T * T3)
    } else {
        delta = -0.000278 + 0.000265 * T + 0.000262 * T2
    }
    return Jd1 + C1 - delta
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
    L = L - PI * 2 * INT(L / (PI * 2)) // Normalize to (0, 2*PI)
    return L
}

fun lunarMonth11(Y: Int): IntArray {
    val off = localToJD(31, 12, Y) - 2415021.076998695
    val k = INT(off / 29.530588853)
    var jd = newMoon(k)
    val ret = localFromJD(jd)
    val sunLong = sunLongitude(localToJD(ret[0], ret[1], ret[2])) // sun
    // longitude
    // at
    // local
    // midnight
    if (sunLong > 3 * PI / 2) {
        jd = newMoon(k - 1)
    }
    return localFromJD(jd)
}

fun lunarYear(Y: Int): Array<IntArray> {
    var ret: Array<IntArray>? = null
    val month11A = lunarMonth11(Y - 1)
    val jdMonth11A = localToJD(month11A[0], month11A[1], month11A[2])
    val k = Math
        .floor(0.5 + (jdMonth11A - 2415021.076998695) / 29.530588853).toInt()
    val month11B = lunarMonth11(Y)
    val off = localToJD(month11B[0], month11B[1], month11B[2]) - jdMonth11A
    val leap = off > 365.0
    if (!leap) {
        ret = Array(13) { IntArray(5) }
    } else {
        ret = Array(14) { IntArray(5) }
    }
    ret[0] = intArrayOf(month11A[0], month11A[1], month11A[2], 0, 0)
    ret[ret.size - 1] = intArrayOf(month11B[0], month11B[1], month11B[2], 0, 0)
    for (i in 1 until ret.size - 1) {
        val nm = newMoon(k + i)
        val a = localFromJD(nm)
        ret[i] = intArrayOf(a[0], a[1], a[2], 0, 0)
    }
    for (i in ret.indices) {
        ret[i][3] = MOD(i + 11, 12)
    }
    if (leap) {
        initLeapYear(ret)
    }
    return ret
}

fun initLeapYear(ret: Array<IntArray>) {
    val sunLongitudes = DoubleArray(ret.size)
    for (i in ret.indices) {
        val a = ret[i]
        val jdAtMonthBegin = localToJD(a[0], a[1], a[2])
        sunLongitudes[i] = sunLongitude(jdAtMonthBegin)
    }
    var found = false
    for (i in ret.indices) {
        if (found) {
            ret[i][3] = MOD(i + 10, 12)
            continue
        }
        val sl1 = sunLongitudes[i]
        val sl2 = sunLongitudes[i + 1]
        val hasMajorTerm = Math.floor(sl1 / PI * 6) != Math.floor(sl2 / PI * 6)
        if (!hasMajorTerm) {
            found = true
            ret[i][4] = 1
            ret[i][3] = MOD(i + 10, 12)
        }
    }
}

/* bat dau chuyen doi ngay am duong */
fun solar2Lunar(D: Int, M: Int, Y: Int): IntArray {
    var yy = Y
    var ly = lunarYear(Y) // Please cache the result of this
    // computation for later use!!!
    val month11 = ly[ly.size - 1]
    val jdToday = localToJD(D, M, Y)
    val jdMonth11 = localToJD(month11[0], month11[1], month11[2])
    if (jdToday >= jdMonth11) {
        ly = lunarYear(Y + 1)
        yy = Y + 1
    }
    var i = ly.size - 1
    while (jdToday < localToJD(ly[i][0], ly[i][1], ly[i][2])) {
        i--
    }
    val dd = (jdToday - localToJD(ly[i][0], ly[i][1], ly[i][2])) as Int + 1
    val mm = ly[i][3]
    if (mm >= 11) {
        yy--
    }
    return intArrayOf(dd, mm, yy, ly[i][4])
}

fun lunar2Solar(D: Int, M: Int, Y: Int, leap: Int): IntArray {
    var yy = Y
    if (M >= 11) {
        yy = Y + 1
    }
    val lunarYear = lunarYear(yy)
    var lunarMonth: IntArray? = null
    for (i in lunarYear.indices) {
        val lm = lunarYear[i]
        if (lm[3] == M && lm[4] == leap) {
            lunarMonth = lm
            break
        }
    }
    if (lunarMonth != null) {
        val jd = localToJD(lunarMonth[0], lunarMonth[1], lunarMonth[2])
        return localFromJD(jd + D - 1)
    } else {
        throw RuntimeException("Incorrect input!")
    }
}

/* ket thuc chuyen doi ngay am duong */

/* bat dau can chi */

fun can(D: Int, M: Int, Y: Int): IntArray {
    val lunar = solar2Lunar(D, M, Y)
    val m = lunar[1]
    val y = lunar[2]

    // return can ngay - thang - nam
    return intArrayOf(INT(localToJD(D, M, Y) + 10.5) % 10, (y * 12 + m + 3) % 10, (y + 6) % 10)
}

fun chi(D: Int, M: Int, Y: Int): IntArray {
    val lunar = solar2Lunar(D, M, Y)
    val m = lunar[1]
    val y = lunar[2]

    // return chi ngay - thang - nam
    return intArrayOf(INT(localToJD(D, M, Y) + 2.5) % 12, (m + 1) % 12, (y + 8) % 12)
}

fun thu(D: Int, M: Int, Y: Int): Int {
    return (localToJD(D, M, Y) + 2.5) as Int % 7
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

fun addDay(D: Int, M: Int, Y: Int, add: Int): IntArray {
    var D = D
    var M = M
    var Y = Y
    D += add
    if (D > maxDayOfMonth(M, Y)) {
        D = 1
        M++
        if (M > 12) {
            M = 1
            Y++
        }
    }

    return intArrayOf(D, M, Y)
}

fun tietKhiMoc(Y: Int): Array<IntArray> {
    val ret = Array(25) { IntArray(2) }
    var add = IntArray(2)

    var i = 0
    var D = 5
    var M = 1

    while (i < 24) {
        if (i == 5)
            while (sunLongitude(localToJD(D, M, Y)) > SUNLONG_MAJOR[6]) {
                D++
            }
        else
            while (sunLongitude(localToJD(D, M, Y)) < SUNLONG_MAJOR[i]) {
                D++
            }
        ret[i][0] = D - 1
        ret[i][1] = M

        add = addDay(D, M, Y, 14)
        D = add[0]
        M = add[1]
        i++
    }

    ret[i][0] = 31
    ret[i][1] = 12
    return ret
}

fun tietKhi(D: Int, M: Int, Y: Int): Int {
    val moc = tietKhiMoc(Y)
    var a: Long
    var b: Long

    if (D == 31 && M == 12)
        return 23
    else {
        for (i in 0..23) {
            a = daysBetween2Dates(moc[i][0], moc[i][1], Y, D, M, Y)
            b = daysBetween2Dates(moc[i][0], moc[i][1], Y, moc[i + 1][0],
                    moc[i + 1][1], Y)
            if (a < 0) {
                return 23
            } else {
                if (a < b) {
                    return i
                }
            }
        }
    }

    return -1
}

fun truc(D: Int, M: Int, Y: Int): Int {
    var d: Int
    var m: Int
    var i: Int

    i = 0
    while (i < 24) {
        d = tietKhiMoc(Y)[i][0]
        m = tietKhiMoc(Y)[i][1]
        if (daysBetween2Dates(D, M, Y, d, m, Y) > 0) {
            return (chi(D, M, Y)[0] + 12 - i / 2) % 12
        }
        i += 2
    }
    return chi(D, M, Y)[0] % 12
}

fun daysBetween2Dates(D1: Int, M1: Int, Y1: Int, D2: Int,
    M2: Int, Y2: Int): Long {

    val c1 = Calendar.getInstance()
    val c2 = Calendar.getInstance()

    // Định nghĩa 2 mốc thời gian ban đầu
    val date1 = Date.valueOf(Y1.toString() + "-" + M1 + "-" + D1)
    val date2 = Date.valueOf(Y2.toString() + "-" + M2 + "-" + D2)

    c1.setTime(date1)
    c2.setTime(date2)

    // Công thức tính số ngày giữa 2 mốc thời gian:

    return (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000)
}

fun nhiThapBatTu(D: Int, M: Int, Y: Int): Int {
    val longDay = daysBetween2Dates(1, 1, 1975, D, M, Y) % 28
    // System.out.println(SAO[(int) longDay]);
    return longDay.toInt()
}

// result=0: ngay hac dao
// result=1: ngay hoang dao
// result=-1: error
fun ngayHoangDao(D: Int, M: Int, Y: Int): Int {
    var D = D
    var M = M
    var Y = Y
    val chi = chi(D, M, Y)[0]
    val lunar = solar2Lunar(D, M, Y)
    D = lunar[0]
    M = lunar[1]
    Y = lunar[2]
    val hoangDao = intArrayOf(1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0)
    var i = -1
    val result: Int

    when (M) {
        1, 7 -> i = 0

        2, 8 -> i = 2

        3, 9 -> i = 4

        4, 10 -> i = 6

        5, 11 -> i = 8

        6, 12 -> i = 10

        else -> i = -1
    }

    if (i != -1) {
        result = (chi - i + 12) % 12
        return hoangDao[result]
    } else
        return -1
}

fun gioHoangDao(D: Int, M: Int, Y: Int): IntArray? {
    val chi = chi(D, M, Y)[0]
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

var countSaoTot: Int = 0

fun saoTot(D: Int, M: Int, Y: Int): IntArray {
    val ret = IntArray(5)
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

    val m = solar2Lunar(D, M, Y)[1]
    val chi = chi(D, M, Y)[0]

    countSaoTot = 0
    for (i in 0..12) {
        if (chi == sao[i][m - 1]) {
            ret[countSaoTot] = i
            countSaoTot++
        }
    }

    return ret
}

var countSaoXau: Int = 0

fun saoXau(D: Int, M: Int, Y: Int): IntArray {
    val ret = IntArray(7)
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

    val m = solar2Lunar(D, M, Y)[1]
    val chi = chi(D, M, Y)[0]

    countSaoXau = 0
    for (i in 0..13) {
        if (chi == saoChi[i][m - 1]) {
            ret[countSaoXau] = i
            countSaoXau++
        }
    }

    val can = can(D, M, Y)[0]
    for (i in 0..1) {
        if (can == saoCan[i][m - 1]) {
            ret[countSaoXau] = i
            countSaoXau++
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

var countNgayBatTuong: Int = 0

fun ngayBatTuong(Y: Int): Array<IntArray> {

    val ret = Array(121) { IntArray(2) }
    var arrBatTuong = arrayBatTuong(0)
    val tietKhi = tietKhiMoc(Y)
    var D = tietKhi[0][0]
    var M = tietKhi[0][1]
    var can: Int
    var chi: Int
    var counter = 0

    while (counter < 24) {
        can = can(D, M, Y)[0]
        chi = chi(D, M, Y)[0]

        arrBatTuong?.let {
            for (i in arrBatTuong!!.indices) {
                if (can == it[i][1] && chi == it[i][0]) {
                    ret[countNgayBatTuong][0] = D
                    ret[countNgayBatTuong][1] = M
                    println(D.toString() + "\t" + M + "\t" + counter)
                    countNgayBatTuong++
                }
            }
        }

        val add = addDay(D, M, Y, 1)
        D = add[0]
        M = add[1]
        if (D == tietKhi[counter + 2][0] && M == tietKhi[counter + 2][1]) {
            arrBatTuong = arrayBatTuong(counter / 2)
            counter += 2
        }
        if (D == 31 && M == 12)
            break
    }

    return ret
}

// 11 ngay tot cho cuoi xin
var countNgayTot: Int = 0

fun ngayTot(Y: Int): Array<IntArray> {
    val ret = Array(70) { IntArray(2) }
    val arrNgayTot = arrayOf(intArrayOf(2, 2), intArrayOf(3, 3), intArrayOf(2, 5), intArrayOf(4, 2),
        intArrayOf(5, 3), intArrayOf(2, 10), intArrayOf(4, 0), intArrayOf(6, 2), intArrayOf(8, 2),
        intArrayOf(9, 3), intArrayOf(1, 5))
    var d: Int
    var m: Int
    var can: Int
    var chi: Int
    countNgayTot = 0
    m = 1
    while (m <= 12) {
        d = 1
        while (d <= maxDayOfMonth(m, Y)) {
            can = can(d, m, Y)[0]
            chi = chi(d, m, Y)[0]

            for (i in 0..10) {
                if (can == arrNgayTot[i][0] && chi == arrNgayTot[i][1]) {
                    ret[countNgayTot][0] = d
                    ret[countNgayTot][1] = m

                    countNgayTot++
                    // System.out.println(d + "\t" + m);
                }
            }
            d++
        }
        m++
    }

    return ret
}

// gap truc thanh cho cuoi xin
var countTrucThanh: Int = 0

fun trucThanh(Y: Int): Array<IntArray> {
    val ret = Array(32) { IntArray(2) }
    var d: Int
    var m: Int
    countTrucThanh = 0
    m = 1
    while (m <= 12) {
        d = 1
        while (d <= maxDayOfMonth(m, Y)) {
            if (truc(d, m, Y) === 8) {
                ret[countTrucThanh][0] = d
                ret[countTrucThanh][1] = m

                countTrucThanh++
                // System.out.println(d + "\t" + m);
            }
            d++
        }
        m++
    }
    return ret
}

fun thangCuoiGa(D: Int, M: Int, Y: Int): IntArray {
    val ret = IntArray(2)
    val chi = chi(D, M, Y)[2]

    when (chi) {
        0, 6 -> {
            ret[0] = 6
            ret[1] = 12
        }
        1, 7 -> {
            ret[0] = 5
            ret[1] = 11
        }
        2, 8 -> {
            ret[0] = 2
            ret[1] = 8
        }
        3, 9 -> {
            ret[0] = 1
            ret[1] = 7
        }
        4, 10 -> {
            ret[0] = 4
            ret[1] = 10
        }
        5, 11 -> {
            ret[0] = 3
            ret[1] = 9
        }
    }
    return ret
}

var countNgayCuoi: Int = 0

fun cuoiGa(D1: Int, M1: Int, D2: Int, M2: Int, Y: Int): Array<IntArray> {
    val ret = Array(100) { IntArray(3) }
    var resultNgayBatTuong = Array(countNgayBatTuong) { IntArray(2) }
    var resultNgayTot = Array(countNgayTot) { IntArray(2) }
    var resultTrucThanh = Array(countTrucThanh) { IntArray(2) }

    var longDays1: Long
    var longDays2: Long
    var d: Int
    var m: Int

    resultNgayBatTuong = ngayBatTuong(Y)
    resultNgayTot = ngayTot(Y)
    resultTrucThanh = trucThanh(Y)

    countNgayCuoi = 0
    println("************")

    for (i in 0 until countNgayBatTuong) {
        d = resultNgayBatTuong[i][0]
        m = resultNgayBatTuong[i][1]
        longDays1 = daysBetween2Dates(d, m, Y, D1, M1, Y)
        longDays2 = daysBetween2Dates(d, m, Y, D2, M2, Y)
        if (longDays1 <= 0 && longDays2 >= 0) {
            ret[countNgayCuoi][0] = d
            ret[countNgayCuoi][1] = m
            countNgayCuoi++

            //				System.out.println(d + "\t" + m + "\t" + THU[thu(d, m, Y)]);
        }
    }

    for (i in 0 until countNgayTot) {
        d = resultNgayTot[i][0]
        m = resultNgayTot[i][1]
        longDays1 = daysBetween2Dates(d, m, Y, D1, M1, Y)
        longDays2 = daysBetween2Dates(d, m, Y, D2, M2, Y)
        if (longDays1 <= 0 && longDays2 >= 0) {
            var counter = 0
            for (j in 0 until countNgayCuoi) {
                if (d == ret[j][0] && m == ret[j][1]) {
                    ret[j][2] = 1
                    break
                } else {
                    counter++
                }
            }
            if (counter == countNgayCuoi) {
                ret[countNgayCuoi][0] = d
                ret[countNgayCuoi][1] = m
                countNgayCuoi++

                //					System.out.println(d + "\t" + m + "\t" + THU[thu(d, m, Y)]);
            }
        }
    }

    for (i in 0 until countTrucThanh) {
        d = resultTrucThanh[i][0]
        m = resultTrucThanh[i][1]
        longDays1 = daysBetween2Dates(d, m, Y, D1, M1, Y)
        longDays2 = daysBetween2Dates(d, m, Y, D2, M2, Y)
        if (longDays1 <= 0 && longDays2 >= 0) {
            var counter = 0
            for (j in 0 until countNgayCuoi) {
                if (d == ret[j][0] && m == ret[j][1]) {
                    ret[j][2] = 1
                    break
                } else {
                    counter++
                }
            }
            if (counter == countNgayCuoi) {
                ret[countNgayCuoi][0] = d
                ret[countNgayCuoi][1] = m
                countNgayCuoi++

                //					System.out.println(d + "\t" + m + "\t" + THU[thu(d, m, Y)]);
            }
        }
    }
    return ret
}