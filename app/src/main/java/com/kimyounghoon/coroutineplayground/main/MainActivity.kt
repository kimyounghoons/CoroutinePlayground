package com.kimyounghoon.coroutineplayground.main

import android.content.Intent
import android.os.Bundle
import com.kimyounghoon.coroutineplayground.base.BaseActivity
import com.kimyounghoon.coroutineplayground.databinding.ActivityMainBinding
import com.kimyounghoon.coroutineplayground.extensions.log
import com.kimyounghoon.coroutineplayground.extensions.logCurrentThread
import com.kimyounghoon.coroutineplayground.lifecycle.LifecycleActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : BaseActivity<ActivityMainBinding>() {
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

            btnLifecycleScope.setOnClickListener {
                Intent(this@MainActivity, LifecycleActivity::class.java).let {
                    startActivity(it)
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