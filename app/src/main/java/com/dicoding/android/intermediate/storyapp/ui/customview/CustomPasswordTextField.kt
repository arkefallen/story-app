package com.dicoding.android.intermediate.storyapp.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.android.intermediate.storyapp.R

class CustomPasswordTextField : AppCompatEditText{

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s != null) {
                        if (s.length >= 0 && s.length < 8) {
                            error = resources.getString(R.string.password_error_notif)
                        } else {
                            error = null
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            }
        )
    }

}