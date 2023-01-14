package com.isayevapps.projectbylectures_easycoderu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val TAG = "MyLog"
    }

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = (application as App).viewModel
        val textView = findViewById<TextView>(R.id.textView)
        val observable = TextObservable()
        observable.observe(object : TextCallback {
            override fun updateText(str: String) = runOnUiThread {
                textView.text = str
            }
        })
        viewModel.init(observable)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        viewModel.resumeCounting()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        viewModel.pauseCounting()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        viewModel.clear()
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}


