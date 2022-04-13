package com.kimyounghoon.coroutineplayground.day5

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kimyounghoon.coroutineplayground.base.BaseActivity
import com.kimyounghoon.coroutineplayground.databinding.ActivityDay5Binding
import com.kimyounghoon.coroutineplayground.extensions.log
import com.kimyounghoon.coroutineplayground.extensions.logCurrentThread
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/*
* async 사용
* */
class Day5Activity : BaseActivity<ActivityDay5Binding>() {

    override fun initView() {
        with(binding) {
            btnMeasureTimeMillis.setOnClickListener {
                lifecycleScope.launch {
                    val time = measureTimeMillis {
                        val hello = getHelloAfter1s()
                        val world = getWorldAfter2s()
                        log(hello + world)
                    }
                    log("time: $time")
                }
            }
            btnAsync.setOnClickListener {
                lifecycleScope.launch {
                    val time = measureTimeMillis {
                        val helloDeferred = async {
                            log("getHelloAfter1s()시작")
                            getHelloAfter1s()
                        }
                        val worldDeferred = async {
                            log("getWorldAfter2s()시작")
                            getWorldAfter2s()
                        }
                        val hello = helloDeferred.await()
                        val world = worldDeferred.await()
                        log(hello + world)
                    }
                    log("time: $time")
                }
            }
            btnAsync2.setOnClickListener {
                lifecycleScope.launch {
                    val time = measureTimeMillis {
                        val helloDeferred = async {
                            log("getHelloAfter1s()시작")
                            getHelloAfter1s()
                        }
                        val worldDeferred = async {
                            log("getWorldAfter2s()시작")
                            getWorldAfter2s()
                        }
                        val world = worldDeferred.await()
                        log(world)
                    }
                    log("time: $time")
                }
            }
            btnLazyAsync.setOnClickListener {
                lifecycleScope.launch {
                    val time = measureTimeMillis {
                        val helloDeferred = async(start = CoroutineStart.LAZY) {
                            log("getHelloAfter1s()시작")
                            getHelloAfter1s()
                        }
                        val worldDeferred = async(start = CoroutineStart.LAZY) {
                            log("getWorldAfter2s()시작")
                            getWorldAfter2s()
                        }

                        helloDeferred.start()
                        worldDeferred.start()
                        val hello = helloDeferred.await()
                        val world = worldDeferred.await()
                        log(hello + world)
                    }
                    log("time: $time")
                }
            }
            btnLazyAsync2.setOnClickListener {
                lifecycleScope.launch {
                    val time = measureTimeMillis {
                        val helloDeferred = async(start = CoroutineStart.LAZY) {
                            log("getHelloAfter1s()시작")
                            getHelloAfter1s()
                        }
                        val worldDeferred = async(start = CoroutineStart.LAZY) {
                            log("getWorldAfter2s()시작")
                            getWorldAfter2s()
                        }

                        //                        worldDeferred.start()
                        val world = worldDeferred.await()
                        log(world)
                    }
                    log("time: $time")
                }
            }
        }
    }

    private suspend fun getHelloAfter1s(): String {
        delay(1000L)
        return "Hello"
    }

    private suspend fun getWorldAfter2s(): String {
        delay(2000L)
        return "World"
    }

}