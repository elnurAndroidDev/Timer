package com.isayevapps.projectbylectures_easycoderu

import java.util.*

class Model(
    private val dataSource: DataSource,
    private val timeTicker: TimeTicker
) {

    private val tickerCallback
        get() = object : TimeTicker.Callback {
            override fun tick() {
                count++
                callback?.updateText(count.toString())
            }
        }

    private var callback: TextCallback? = null
    private var timer: Timer? = null

    private var count = -1

    fun start(callback: TextCallback) {
        this.callback = callback
        if (count < 0)
            count = dataSource.getInt(COUNTER_KEY)
        timer = Timer()
        timeTicker.start(tickerCallback)
    }

    fun stop() {
        dataSource.saveInt(COUNTER_KEY, count)
        timeTicker.stop()
    }

    companion object {
        private const val COUNTER_KEY = "keyInt"
    }
}