package com.isayevapps.projectbylectures_easycoderu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contentLayout = findViewById<LinearLayout>(R.id.contentLayout)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val textInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        val emailTextView = textInputLayout.editText as TextInputEditText
        emailTextView.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
                textInputLayout.isErrorEnabled = !isValid
                val error = if (isValid) "" else getString(R.string.invalid_email)
                textInputLayout.error = error
                if (isValid) Toast.makeText(
                    this@MainActivity,
                    getString(R.string.valid_email),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val agreementTextView: TextView = findViewById(R.id.agreementTextView)

        val fullText = getString(R.string.agreement_fulltext)
        val confidential = getString(R.string.terms_of_use)
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
        }
        val loginButton = findViewById<Button>(R.id.loginButton)
        val checkbox = findViewById<CheckBox>(R.id.agreementCheckBox)

        loginButton.isEnabled = false
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            loginButton.isEnabled = isChecked
        }

        loginButton.setOnClickListener {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailTextView.text.toString())
                    .matches()
            ) {
                contentLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                Snackbar.make(loginButton, "post login", Snackbar.LENGTH_SHORT).show()
                val dialog = BottomSheetDialog(this)
                val view = LayoutInflater.from(this).inflate(R.layout.dialog, contentLayout, false)
                dialog.setCancelable(false)
                Handler(Looper.myLooper()!!).postDelayed({
                    contentLayout.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    dialog.setContentView(view)
                    dialog.show()
                    view.findViewById<View>(R.id.cancelButton).setOnClickListener {
                        dialog.dismiss()
                    }
                }, 3000)
            }
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


abstract class SimpleTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}
