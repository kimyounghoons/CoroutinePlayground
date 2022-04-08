package com.kimyounghoon.coroutineplayground.day5

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kimyounghoon.coroutineplayground.base.BaseActivity
import com.kimyounghoon.coroutineplayground.databinding.ActivityDay5Binding
import com.kimyounghoon.coroutineplayground.extensions.log
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/*
* async 사용
* */
class Day5Activity : BaseActivity<ActivityDay5Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        val helloDeffered = async {
                            log("getHelloAfter1s()시작")
                            getHelloAfter1s()
                        }
                        val worldDeffered = async {
                            log("getWorldAfter2s()시작")
                            getWorldAfter2s()
                        }
                        val hello = helloDeffered.await()
                        val world = worldDeffered.await()
                        log(hello + world)
                    }
                    log("time: $time")
                }
            }
            btnAsync2.setOnClickListener {
                lifecycleScope.launch {
                    val time = measureTimeMillis {
                        val helloDeffered = async {
                            log("getHelloAfter1s()시작")
                            getHelloAfter1s()
                        }
                        val worldDeffered = async {
                            log("getWorldAfter2s()시작")
                            getWorldAfter2s()
                        }
                        val world = worldDeffered.await()
                        log(world)
                    }
                    log("time: $time")
                }
            }
            btnLazyAsync.setOnClickListener {
                lifecycleScope.launch {
                    val time = measureTimeMillis {
                        val helloDeffered = async(start = CoroutineStart.LAZY) {
                            log("getHelloAfter1s()시작")
                            getHelloAfter1s()
                        }
                        val worldDeffered = async(start = CoroutineStart.LAZY) {
                            log("getWorldAfter2s()시작")
                            getWorldAfter2s()
                        }

                        helloDeffered.start()
                        worldDeffered.start()
                        val hello = helloDeffered.await()
                        val world = worldDeffered.await()
                        log(hello + world)
                    }
                    log("time: $time")
                }
            }
            btnLazyAsync2.setOnClickListener {
                lifecycleScope.launch {
                    val time = measureTimeMillis {
                        val helloDeffered = async(start = CoroutineStart.LAZY) {
                            log("getHelloAfter1s()시작")
                            getHelloAfter1s()
                        }
                        val worldDeffered = async(start = CoroutineStart.LAZY) {
                            log("getWorldAfter2s()시작")
                            getWorldAfter2s()
                        }

                        worldDeffered.start()
                        val world = worldDeffered.await()
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