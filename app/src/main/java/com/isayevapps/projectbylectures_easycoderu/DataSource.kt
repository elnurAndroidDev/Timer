package com.isayevapps.projectbylectures_easycoderu

interface DataSource {

    fun saveInt(key: String, value: Int)
    fun getInt(key: String): Int
}