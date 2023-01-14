package com.isayevapps.projectbylectures_easycoderu

import java.util.*

class Model(private val dataSource: DataSource) {

    private var callback: TextCallback? = null
    private var timer: Timer? = null
    private val timerTask
        get() = object : TimerTask() {
            override fun run() {
                count++
                callback?.updateText(count.toString())
            }
        }

    private var count = -1

    fun start(callback: TextCallback) {
        this.callback = callback
        if (count < 0)
            count = dataSource.getInt(COUNTER_KEY)
        timer = Timer()
        timer?.scheduleAtFixedRate(timerTask, 0, 1000)
    }

    fun stop() {
        dataSource.saveInt(COUNTER_KEY, count)
        timer?.cancel()
        timer = null
    }

    companion object {
        private const val COUNTER_KEY = "keyInt"
    }
}