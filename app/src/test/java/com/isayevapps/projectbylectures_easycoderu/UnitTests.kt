package com.isayevapps.projectbylectures_easycoderu

import org.junit.Test

import org.junit.Assert.*

class UnitTests {
    @Test
    fun start_with_saved_value() {
        val testDataSource = TestDataSource()
        val timer = TestTimerTicker()
        val model = Model(testDataSource, timer)
        val callback = TestCallBack()
        testDataSource.saveInt("", 5)
        model.start(callback)
        timer.tick(1)
        val actual = callback.text
        val expected = "6"
        assertEquals(expected, actual)
    }

    @Test
    fun stop_after_2_seconds() {
        val testDataSource = TestDataSource()
        val timer = TestTimerTicker()
        val model = Model(testDataSource, timer)
        val callback = TestCallBack()
        testDataSource.saveInt("", 0)
        model.start(callback)
        timer.tick(2)
        val actual = callback.text
        val expected = "2"
        assertEquals(expected, actual)
        model.stop()
        val savedValue = testDataSource.getInt("")
        val savedValueExpected = 2
        assertEquals(savedValueExpected, savedValue)
    }

    @Test
    fun start_after_stop() {
        val testDataSource = TestDataSource()
        val timer = TestTimerTicker()
        val model = Model(testDataSource, timer)
        val callback = TestCallBack()
        testDataSource.saveInt("", 5)
        model.start(callback)
        timer.tick(6)
        val actual = callback.text
        val expected = "11"
        assertEquals(expected, actual)
        model.stop()
        val savedValue = testDataSource.getInt("")
        val savedValueExpected = 11
        assertEquals(savedValueExpected, savedValue)

        model.start(callback)
        timer.tick(6)
        val actual2 = callback.text
        val expected2 = "17"
        assertEquals(expected2, actual2)
    }


}

private class TestCallBack : TextCallback {
    var text = ""
    override fun updateText(str: String) {
        text = str
    }
}

private class TestDataSource : DataSource {
    private var int: Int = Int.MIN_VALUE
    override fun saveInt(key: String, value: Int) {
        int = value
    }

    override fun getInt(key: String): Int {
        return int
    }
}

private class TestTimerTicker : TimeTicker {

    private var callback: TimeTicker.Callback? = null
    var state = 0

    override fun start(callback: TimeTicker.Callback, period: Long) {
        this.callback = callback
        state = 1
    }

    override fun stop() {
        callback = null
        state = -1
    }

    fun tick(times: Int) {
        for (i in 0 until times) {
            callback?.tick()
        }
    }

}