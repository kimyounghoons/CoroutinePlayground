package com.kimyounghoon.coroutineplayground.day7

import androidx.lifecycle.lifecycleScope
import com.kimyounghoon.coroutineplayground.base.MainItemRecyclerActivity
import com.kimyounghoon.coroutineplayground.extensions.log
import com.kimyounghoon.coroutineplayground.model.MainItem
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.*
import kotlin.system.measureTimeMillis

class Day7Activity : MainItemRecyclerActivity() {
    @Volatile
    var volatileCounter = 0

    val counterContext = newSingleThreadContext("CounterContext")

    override fun getRecyclerListener(): (Int) -> Unit = { position ->
        when (position) {
            0 -> {
                lifecycleScope.launch(Dispatchers.Default) {
                    var count = 0
                    massiveRun {
                        count++
                    }

                    log("count : $count")
                }
            }
            1 -> {
                volatileCounter = 0
                lifecycleScope.launch(Dispatchers.Default) {
                    massiveRun {
                        volatileCounter++
                    }
                    log("volatileCounter : $volatileCounter")
                }
            }
            2 -> {
                val atomicCounter = AtomicInteger()
                lifecycleScope.launch(Dispatchers.Default) {
                    massiveRun {
                        atomicCounter.incrementAndGet()
                    }

                    log("atomicCounter : $atomicCounter")
                }
            }
            3 -> {
                lifecycleScope.launch(Dispatchers.Default) {
                    var singleThreadContextCounter = 0
                    withContext(counterContext) {
                        massiveRun {
                            singleThreadContextCounter++
                        }
                    }
                    log("singleThreadContextCounter : $singleThreadContextCounter")
                }
            }
            4 -> {
                lifecycleScope.launch(Dispatchers.Default) {
                    val mutex = Mutex()
                    var mutexCounter = 0
                    massiveRun {
                        mutex.withLock {
                            mutexCounter++
                        }
                    }
                    log("mutexCounter : $mutexCounter")
                }
            }
            5 -> {
                lifecycleScope.launch(Dispatchers.Default) {
                    val counter = counterActor()

                    massiveRun {
                        counter.send(IncCounter)
                    }

                    val response = CompletableDeferred<Int>()
                    counter.send(GetCounter(response))
                    log("Counter = ${response.await()}")
                    counter.close()
                }
            }
        }
    }

    sealed class CounterMsg
    object IncCounter : CounterMsg()
    class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

    fun CoroutineScope.counterActor() = actor<CounterMsg> {
        var counter = 0
        for (msg in channel) {
            when (msg) {
                is IncCounter -> counter++
                is GetCounter -> msg.response.complete(counter)
            }
        }
    }

    override fun getMainItems(): List<MainItem> = listOf(
        MainItem(content = "count Up"),
        MainItem(content = "volatile"),
        MainItem(content = "Atomic"),
        MainItem(content = "single Thread"),
        MainItem(content = "mutex"),
        MainItem(content = "actors")
    )

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100
        val k = 1000
        val time = measureTimeMillis {
            coroutineScope {
                repeat(n) {
                    launch {
                        repeat(k) { action() }
                    }
                }
            }
        }
        log("${n * k}번 action 실행 ,실행 시간 : $time ms")
    }

}