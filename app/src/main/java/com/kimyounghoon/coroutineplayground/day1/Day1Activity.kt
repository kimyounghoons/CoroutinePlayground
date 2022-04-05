package com.kimyounghoon.coroutineplayground.day1

import android.os.Bundle
import com.kimyounghoon.coroutineplayground.base.BaseActivity
import com.kimyounghoon.coroutineplayground.databinding.ActivityDay1Binding
import com.kimyounghoon.coroutineplayground.extensions.logCurrentThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
* runBlocking 과 CoroutineScope 차이
* launch 와 delay 사용
* */
class Day1Activity : BaseActivity<ActivityDay1Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            btnRunBlocking.setOnClickListener {
                runBlocking {
                    logCurrentThread("runBlocking")
                }
            }
            btnLaunchInsideRunBlocking.setOnClickListener {
                runBlocking {
                    logCurrentThread("runBlocking1")
                    launch {
                        logCurrentThread("launch")
                    }
                    logCurrentThread("runBlocking2")
                }
            }
            btnLaunchInsideRunBlockingWithDelay.setOnClickListener {
                runBlocking {
                    logCurrentThread("runBlocking1")
                    launch {
                        logCurrentThread("launch")
                    }
                    delay(3000)
                    logCurrentThread("runBlocking2")
                }
            }
            btnCoroutineScope.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    logCurrentThread("CoroutineScope")
                }
            }
            btnLaunchInsideCoroutineScope.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    logCurrentThread("CoroutineScope")
                    launch {
                        logCurrentThread("launch")
                    }
                    logCurrentThread("CoroutineScope2")
                }
            }
            btnLaunchInsideCoroutineScopeWithDelay.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    logCurrentThread("CoroutineScope")
                    launch {
                        logCurrentThread("launch")
                    }
                    delay(3000)
                    logCurrentThread("CoroutineScope2")
                }
            }
            btnLightWeight.setOnClickListener {
                CoroutineScope(Dispatchers.Default).launch {
                    repeat(10_000) {
                        launch {
                            delay(1L)
                            logCurrentThread("launch")
                        }
                    }
                }
            }
        }
    }
}