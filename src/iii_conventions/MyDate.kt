package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> = DateIterator(this)

    override fun contains(value: MyDate): Boolean {
        return start <= value && value <= endInclusive
    }
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {

    var current: MyDate = dateRange.start

    override fun hasNext(): Boolean = current <= dateRange.endInclusive

    override fun next(): MyDate {
        val result = current
        current = MyDate(current.year, current.month, current.dayOfMonth + 1)
        return result
    }

}