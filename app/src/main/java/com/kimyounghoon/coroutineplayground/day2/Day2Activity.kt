package com.kimyounghoon.coroutineplayground.day2

import android.os.Bundle
import com.kimyounghoon.coroutineplayground.base.BaseActivity
import com.kimyounghoon.coroutineplayground.databinding.ActivityDay2Binding
import com.kimyounghoon.coroutineplayground.extensions.log
import com.kimyounghoon.coroutineplayground.extensions.logCurrentThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
* job 의 join 기능과 suspend fun 사용
* */
class Day2Activity : BaseActivity<ActivityDay2Binding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            btnJobJoin.setOnClickListener {
                CoroutineScope(Dispatchers.Default).launch {
                    logCurrentThread("1")
                    val job = launch {
                        logCurrentThread("2")
                        delay(3000L)
                        logCurrentThread("3")
                    }
                    job.join()
                    logCurrentThread("4") //1 ,4 , 2,3
                }
            }
            btnSuspendFun.setOnClickListener {
                CoroutineScope(Dispatchers.Default).launch {
                    logHelloWorld()
                }
            }
            launchInsideSuspendFun.setOnClickListener {
                CoroutineScope(Dispatchers.Default).launch {
                    logHelloWorld2()
                }
            }
        }
    }

    private suspend fun logHelloWorld() {
        log("Hello")
        delay(1000L)
        log("World")
    }

    private suspend fun logHelloWorld2() {
        coroutineScope {
            launch {
                log("Hello")
            }

            launch {
                log("World")
            }
        }
    }

}