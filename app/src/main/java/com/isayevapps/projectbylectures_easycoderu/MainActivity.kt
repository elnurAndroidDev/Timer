package com.isayevapps.projectbylectures_easycoderu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val agreementTextView: TextView = findViewById(R.id.agreementTextView)

        val fullText = getString(R.string.agreement_fulltext)
        val confidential = getString(R.string.confidential_info)
        val policy = getString(R.string.privacy_policy)
        val spannableString = SpannableString(fullText)
        val confidentialSpan = MyClickableSpan {
            Snackbar.make(it, "Go to Link 1", Snackbar.LENGTH_SHORT).show()
        }
        val policySpan = MyClickableSpan {
            Snackbar.make(it, "Go to Link 2", Snackbar.LENGTH_SHORT).show()
        }

        spannableString.setSpan(
            confidentialSpan,
            fullText.indexOf(confidential),
            fullText.indexOf(confidential) + confidential.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            policySpan,
            fullText.indexOf(policy),
            fullText.indexOf(policy) + policy.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        agreementTextView.run {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.BLUE
        }
    }
}

class MyClickableSpan(private val lambda: (view: View) -> Unit) : ClickableSpan() {
    override fun onClick(widget: View) = lambda.invoke(widget)
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = true
        ds.color = Color.rgb(255, 0, 0)
    }
}