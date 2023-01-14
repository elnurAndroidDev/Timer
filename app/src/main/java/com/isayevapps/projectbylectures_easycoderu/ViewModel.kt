package com.isayevapps.projectbylectures_easycoderu

class ViewModel(private val model: Model) {

    private var textObservable: TextObservable? = null
    private val textCallback = object : TextCallback {
        override fun updateText(str: String) {
            textObservable?.postValue(str)
        }
    }

    fun init(textObservable: TextObservable) {
        this.textObservable = textObservable
    }

    fun resumeCounting(){
        model.start(textCallback)
    }

    fun pauseCounting(){
        model.stop()
    }

    fun clear() {
        textObservable = null
    }
}