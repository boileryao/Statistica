package com.boileryao.statistica.math

import org.junit.Assert
import org.junit.Test

/**
 * Created by boileryao on 2019/10/16.
 */
class StatisticMathKtTest {

    @Test
    fun rsd_SmartQz_01() {
        val nums = doubleArrayOf(5.1226, 5.1311, 5.1245)
        Assert.assertEquals(0.00087032, rsd(nums), 10e-8)
    }

    @Test
    fun rsd_Wikipedia_01() {
        val nums = doubleArrayOf(1.0, 5.0, 6.0, 8.0, 10.0, 40.0, 65.0, 88.0)
        Assert.assertEquals(27.9, average(nums), 10e-1)
        Assert.assertEquals(32.9, sd(nums), 10e-1)
        Assert.assertEquals(1.18, rsd(nums), 10e-1)
    }
}
