package com.kimyounghoon.coroutineplayground.Day8

import androidx.lifecycle.lifecycleScope
import com.kimyounghoon.coroutineplayground.base.MainItemRecyclerActivity
import com.kimyounghoon.coroutineplayground.extensions.log
import com.kimyounghoon.coroutineplayground.model.MainItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class Day8Activity : MainItemRecyclerActivity() {
    override fun getRecyclerListener(): (Int) -> Unit = { position ->
        when (position) {
            0 -> { //FlowCollect
                lifecycleScope.launch {
                    log("launch")
                    val flow = simple()
                    log("첫번째 collect 시작")
                    flow.collect { value -> log("첫번째 $value") }
                    log("첫번째 collect 끝")
                    log("두번째 collect 시작")
                    flow.collect { value -> log("두번째 $value") }
                    log("두번째 collect 끝")
                }
            }
            1 -> {
                lifecycleScope.launch {
                    (1..3).asFlow().collect { value -> log(value.toString()) }
                }
            }
            2 -> {
                lifecycleScope.launch {
                    flowOf(1,2,3).collect { value -> log(value.toString()) }
                }
            }
            3 -> {
                lifecycleScope.launch {
                    withTimeoutOrNull(250) { // 250ms 후에 타임아웃
                        simple().collect { value -> log(value.toString()) }
                    }
                    log("Done")
                }
            }
        }
    }

    override fun getMainItems(): List<MainItem> = listOf(
        MainItem(content = "flow"),
        MainItem(content = "as Flow"),
        MainItem(content = "flow Of"),
        MainItem(content = "withTimeoutOrNull"),
    )

    private fun simple(): Flow<Int> = flow {
        log("Flow 시작")
        for (i in 1..3) {
            delay(100)
            log("emit $i")
            emit(i)
        }
    }

}