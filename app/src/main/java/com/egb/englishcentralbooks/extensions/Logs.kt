package com.egb.englishcentralbooks.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast


internal fun logInfo(msg: String = "", tag: String = "-") {
    if (com.egb.englishcentralbooks.BuildConfig.DEBUG) {
        Log.i(tag, msg)
    }
}

internal fun logError(msg: String = "", tag: String = "-") {
    if (com.egb.englishcentralbooks.BuildConfig.DEBUG) {
        Log.e(tag, msg)
    }
}

internal fun toast(str: CharSequence?, context: Context?) = Toast.makeText(context, str, Toast.LENGTH_SHORT).show()