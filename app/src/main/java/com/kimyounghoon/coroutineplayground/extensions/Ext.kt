package com.kimyounghoon.coroutineplayground.extensions

import android.util.Log

fun Any.logCurrentThread(title: String) {
    Log.e("kyh!!!", "$title : ${Thread.currentThread().name}")
}

fun Any.log(content: String) {
    Log.e("kyh!!!", content)
}