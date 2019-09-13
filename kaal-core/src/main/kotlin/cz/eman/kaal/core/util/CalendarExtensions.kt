package cz.eman.kaal.core.util

import java.util.*

val Calendar.year: Int
    get() = this.get(Calendar.YEAR)

val Calendar.month: Int
    get() = this.get(Calendar.MONTH)

val Calendar.dayOfMonth: Int
    get() = this.get(Calendar.DAY_OF_MONTH)

val Calendar.dayOfYear: Int
    get() = this.get(Calendar.DAY_OF_YEAR)

fun Calendar.normalize(): Calendar {
    this.set(Calendar.HOUR, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
    return this
}

/**
 * @since 0.3.0
 */
fun Calendar.cloneCalendar(): Calendar {
    return this.clone() as Calendar
}

/**
 * @since 0.3.0
 */
fun Calendar.isDayLessThan(calendar: Calendar): Boolean {
    return when {
        year < calendar.year -> true
        year == calendar.year -> dayOfYear < calendar.dayOfYear
        else -> false
    }
}

/**
 * @since 0.3.0
 */
fun Calendar.isSameDay(calendar: Calendar): Boolean {
    return year == calendar.year && dayOfYear == calendar.dayOfYear
}

/**
 * @since 0.3.0
 */
fun Calendar.isDayGreaterThan(calendar: Calendar): Boolean {
    return when {
        isDayLessThan(calendar) -> false
        isSameDay(calendar) -> false
        else -> true
    }
}

fun Long.timestampToCalendar(): Calendar {
    val resultCalendar = Calendar.getInstance()
    resultCalendar.timeInMillis = this
    return resultCalendar
}
