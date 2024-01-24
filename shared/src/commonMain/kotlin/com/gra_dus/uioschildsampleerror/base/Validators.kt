package com.gra_dus.uioschildsampleerror.base


fun String.checkMax(max: Int): Boolean {
    return count() > max
}

fun String.checkMin(min: Int): Boolean {
    return count() < min
}

fun String.checkRange(range: IntRange): Boolean {
    return count() !in range
}

fun String.checkByRegex(regex: Regex): Boolean {
    return !contains(regex)
}
