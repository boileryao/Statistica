package com.boileryao.statistica.math

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by boileryao on 2019/10/16.
 */

/**
 * Relative Standard Deviation
 */
fun rsd(nums: DoubleArray): Double {
    return sd(nums) / average(nums)
}

/**
 * Standard Deviation
 */
fun sd(nums: DoubleArray): Double {
    if (nums.size <= 1) return 0.0

    val avg = average(nums)
    val squaredDiffs = nums.sumByDouble { num -> (num - avg).pow(2) }
    val deviation = squaredDiffs / (nums.size - 1)

    return sqrt(deviation)
}

/**
 * Average
 */
fun average(nums: DoubleArray): Double {
    return nums.average()
}
