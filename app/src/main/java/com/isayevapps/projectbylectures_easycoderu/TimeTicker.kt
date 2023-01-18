package com.isayevapps.projectbylectures_easycoderu

interface TimeTicker {
    fun start(callback: Callback, period: Long = 1000)
    fun stop()
    interface Callback {
        fun tick()
    }
}