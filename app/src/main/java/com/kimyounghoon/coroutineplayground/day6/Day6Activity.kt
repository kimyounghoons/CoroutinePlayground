package com.kimyounghoon.coroutineplayground.day6

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimyounghoon.coroutineplayground.base.BaseActivity
import com.kimyounghoon.coroutineplayground.databinding.ActivityDay6Binding
import com.kimyounghoon.coroutineplayground.extensions.log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

/*
* 코루틴 컨텍스트와 디스패처
* */
@ExperimentalStdlibApi
class Day6Activity : BaseActivity<ActivityDay6Binding>() {
    private lateinit var _adapter: StudentRecyclerAdapter
    private var dispatcher = Dispatchers.Main
    private val singleThreadContext = newSingleThreadContext("코루틴")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnclickListeners()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(dispatcher) {
            _adapter.addRandomStudents()
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            _adapter = StudentRecyclerAdapter {
                lifecycleScope.launch(dispatcher) {
                    _adapter.addRandomStudents()
                }
            }
            rvMain.adapter = _adapter
            rvMain.layoutManager = LinearLayoutManager(this@Day6Activity)
        }
    }

    private fun setOnclickListeners() {
        with(binding) {
            btnDispatchers.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    launch(CoroutineName("부모의 컨텍스트")) {
                        log("Parent Context / ${Thread.currentThread().name} , ${coroutineContext[CoroutineName]} , ${coroutineContext[CoroutineDispatcher]}")
                        delay(1000)
                        log("Parent Context / ${Thread.currentThread().name} , ${coroutineContext[CoroutineName]} , ${coroutineContext[CoroutineDispatcher]}")
                    }

                    launch(Dispatchers.IO) {
                        launch(Dispatchers.Default + CoroutineName("디폴트")) {
                            log("Default / ${Thread.currentThread().name} , ${coroutineContext[CoroutineName]} , ${coroutineContext[CoroutineDispatcher]}")
                            delay(1000)
                            log("Default / ${Thread.currentThread().name} , ${coroutineContext[CoroutineName]} , ${coroutineContext[CoroutineDispatcher]}")
                        }
                    }

                    launch(Dispatchers.IO + CoroutineName("아이오")) {
                        log("IO / ${Thread.currentThread().name} , ${coroutineContext[CoroutineName]} , ${coroutineContext[CoroutineDispatcher]}")
                        delay(1000)
                        log("IO / ${Thread.currentThread().name}, ${coroutineContext[CoroutineName]} , ${coroutineContext[CoroutineDispatcher]}")
                    }

                    launch(Dispatchers.IO) {
                        launch(Dispatchers.Unconfined + CoroutineName("언컨파인드")) {
                            log("Unconfined / ${Thread.currentThread().name}, ${coroutineContext[CoroutineName]} , ${coroutineContext[CoroutineDispatcher]}")
                            delay(1000)
                            log("Unconfined / ${Thread.currentThread().name}, ${coroutineContext[CoroutineName]} , ${coroutineContext[CoroutineDispatcher]}")
                        }
                    }

                    launch(Dispatchers.IO) {
                        launch(singleThreadContext + CoroutineName("newSingleThreadContext!!!")) {
                            log("newSingleThreadContext / ${Thread.currentThread().name}, ${coroutineContext[CoroutineName]}, ${coroutineContext[CoroutineDispatcher]}")
                            delay(1000)
                            log("newSingleThreadContext / ${Thread.currentThread().name}, ${coroutineContext[CoroutineName]}, ${coroutineContext[CoroutineDispatcher]}")
                        }
                    }
                }
            }

            btnMain.setOnClickListener {
                log("1")
                lifecycleScope.launch {
                    log("2")
                    launch(Dispatchers.Main) {
                        log("3")
                    }
                    log("4")
                }
                log("5")
            }

            btnMainImmediate.setOnClickListener {
                log("1")
                lifecycleScope.launch {
                    log("2")
                    launch(Dispatchers.Main.immediate) {
                        log("3")
                    }
                    log("4")
                }
                log("5")
            }
        }
    }

}