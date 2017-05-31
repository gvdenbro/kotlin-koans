package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(interval: TimeInterval): MyDate {
    return this.addTimeIntervals(interval, 1)
}

operator fun MyDate.plus(interval: RepeatedTimeInterval): MyDate {
    return this.addTimeIntervals(interval.ti, interval.n)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

data class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

operator fun TimeInterval.times(i: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, i)
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