package com.kimyounghoon.coroutineplayground.day9

import androidx.lifecycle.lifecycleScope
import com.kimyounghoon.coroutineplayground.base.MainItemRecyclerActivity
import com.kimyounghoon.coroutineplayground.extensions.log
import com.kimyounghoon.coroutineplayground.model.MainItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class Day9Activity : MainItemRecyclerActivity() {
    override fun getRecyclerListener(): (Int) -> Unit = { position ->
        when (position) {
            0 -> {
                lifecycleScope.launch {
                    (1..10).asFlow()
                        .map { value -> performValue(value) }
                        .collect { value -> log("collect : $value") }
                }
            }
            1 -> {
                lifecycleScope.launch {
                    (1..10).asFlow()
                        .transform { value ->
                            emit("transform emit : $value")
                            emit(performValue(value))
                        }
                        .collect { value -> log("collect : $value") }
                }
            }
            2 -> {
                lifecycleScope.launch {
                    numbers().take(2)
                        .collect { value -> log("collect : $value") }
                }
            }
            3 -> {
                lifecycleScope.launch {
                    numbers().takeWhile {
                        it < 3
                    }.collect { value -> log("collect : $value") }
                }
            }
            4 -> {
                lifecycleScope.launch {
                    numbers().drop(2)
                        .collect { value -> log("collect : $value") }
                }
            }
            5 -> {
                lifecycleScope.launch {
                    val value = (1..5)
                        .asFlow()
                        .reduce { first, second ->
                            log("first: $first , second : $second")
                            first + second
                        }
                    log("value : $value")
                }
            }
            6 -> {
                lifecycleScope.launch {
                    val value = (1..5)
                        .asFlow()
                        .fold(1) { first, second ->
                            log("first: $first , second : $second")
                            first + second
                        }
                    log("value : $value")
                }
            }
        }
    }

    private fun numbers(): Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            log("아래 emit은 실행되지 않음")
            emit(3)
        } finally {
            log("emit 후 보장 되는 영역")
        }
    }

    private suspend fun performValue(value: Int): String {
        delay(1000)
        return "performValue: $value"
    }

    override fun getMainItems(): List<MainItem> = listOf(
        MainItem(content = "map"),
        MainItem(content = "transform"),
        MainItem(content = "take"),
        MainItem(content = "takeWhile"),
        MainItem(content = "drop"),
        MainItem(content = "reduce"),
        MainItem(content = "fold"),
    )

}