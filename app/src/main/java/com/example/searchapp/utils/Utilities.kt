package com.example.searchapp.utils

import android.view.View
import android.widget.Toast
import com.example.searchapp.view.App


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun String.showAsToast() {
    Toast.makeText(App.getAppContext(), this, Toast.LENGTH_LONG).show()
}
