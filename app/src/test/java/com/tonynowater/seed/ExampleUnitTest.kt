package com.tonynowater.seed

import org.junit.Test

import org.junit.Assert.*
import java.util.Calendar

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val start = Calendar.getInstance()
        val end = Calendar.getInstance()

        println("start = ${System.identityHashCode(start)}")
        println("end = ${System.identityHashCode(end)}")

        assertFalse(start == end)
    }
}
