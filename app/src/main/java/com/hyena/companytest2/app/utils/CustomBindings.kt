package com.hyena.companytest2.app.utils

import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("makeTextViewScrollable")
fun makeTextViewScrollable(textView: TextView, scrollablable: Boolean) {
    textView.isVerticalScrollBarEnabled = scrollablable
    textView.movementMethod = ScrollingMovementMethod()
}