package com.egb.englishcentralbooks.extensions

import androidx.fragment.app.Fragment

internal inline fun <reified T : Any> Fragment.argument(key: String): Lazy<T?> {
    return lazy(LazyThreadSafetyMode.NONE) {
        arguments?.get(key) as? T
    }
}