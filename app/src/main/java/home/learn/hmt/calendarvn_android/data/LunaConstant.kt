package home.learn.hmt.calendarvn_android.data

const val LOCAL_TIMEZONE = 7.0
val GIO = arrayListOf<String>("23h-1h", "1h-3h", "3h-5h",
        "5h-7h", "7h-9h", "9h-11h", "11h-13h", "13h-15h", "15h-17h",
        "17h-19h", "19h-21h", "21h-23h")

val THU = arrayListOf<String>("Chu nhat", "Thu hai", "Thu ba",
        "Thu tu", "Thu nam", "Thu sau", "Thu bay")

val CHI = arrayListOf<String>("Ty (chuot)", "Suu", "Dan",
        "Mao", "Thin", "Ty (ran)", "Ngo", "Mui", "Than", "Dau", "Tuat",
        "Hoi")

val CAN = arrayListOf<String>("Giap", "At", "Binh", "Dinh",
        "Mau", "Ky", "Canh", "Tan", "Nham", "Quy")

fun Int.MOD(x: Int, y: Int): Int {
    var result = x - ((y * Math.floor((x / y) as Double)) as Int)
    if (result == 0) {
        result = y
    }
    return result
}

// doi duong lich ra julius
fun universalToJD(D: Int, M: Int, Y: Int): Double {
    val JD: Double
    if (Y > 1582 || Y == 1582 && M > 10
            || Y == 1582 && M == 10 && D > 14) {
        JD = ((367 * Y - (7 * (Y + ((M + 9) / 12).toInt()) / 4)
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
    Z = (JD + 0.5).toInt()
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


// result=0: ngay hac dao
// result=1: ngay hoang dao
// result=-1: error
/*fun ngayHoangDao(D: Int, M: Int, Y: Int): Int {
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
}*/

/*
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
}*/
