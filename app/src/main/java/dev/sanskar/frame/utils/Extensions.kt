package dev.sanskar.frame.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Context.showKeyboard() {
    val inputMethodManager: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


}

fun Context.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}