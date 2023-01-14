package com.isayevapps.projectbylectures_easycoderu

import java.util.*

class Model {

    private lateinit var callback: TextCallback
    private var timer: Timer? = null
    private val timerTask = object : TimerTask() {
        override fun run() {
            count++
            callback.updateText(count.toString())
        }
    }

    private var count = 0

    fun start(callback: TextCallback) {
        this.callback = callback
        if (timer == null) {
            timer = Timer()
            timer?.scheduleAtFixedRate(timerTask, 1000, 1000)
        }
    }
}