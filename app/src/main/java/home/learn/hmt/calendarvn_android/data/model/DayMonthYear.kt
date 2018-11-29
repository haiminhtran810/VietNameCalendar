package home.learn.hmt.calendarvn_android.data.model

class DayMonthYear {
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0
    var nm: Int = 0
    var leap: Int = 0
    var full: Int = 0

    constructor(day: Int, month: Int, year: Int, nm: Int, leap: Int) {
        this.day = day
        this.month = month
        this.year = year
        this.nm = nm
        this.leap = leap
    }

    constructor(day: Int, month: Int, year: Int, leap: Int) {
        this.day = day
        this.month = month
        this.year = year
        this.leap = leap
    }

    constructor(day: Int, month: Int, year: Int) {
        this.day = day
        this.month = month
        this.year = year
    }

    constructor(day: Int, month: Int) {
        this.day = day
        this.month = month
        this.year = 0
    }

    constructor() {}
}