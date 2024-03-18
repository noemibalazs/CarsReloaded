package com.noemi.carsreloaded.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun Activity.hideSoftKeyBoard(editText: EditText) {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
}

fun Float.meterToKm(): Double {
    val kmUnformatted = (this / 1000).toDouble()
    return String.format("%.2f", kmUnformatted).toDouble()
}