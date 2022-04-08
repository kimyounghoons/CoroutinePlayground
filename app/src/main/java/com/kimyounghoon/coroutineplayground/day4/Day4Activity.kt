package com.kimyounghoon.coroutineplayground.day4

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.kimyounghoon.coroutineplayground.base.BaseActivity
import com.kimyounghoon.coroutineplayground.databinding.ActivityDay4Binding
import com.kimyounghoon.coroutineplayground.extensions.logCurrentThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

/*
* CancelAndJoin , isActive 사용해서 Job 상태 확인 하기 , 리소스 정리, 취소 불가능한 잡, 잡에 타임아웃 걸기
* */
class Day4Activity : BaseActivity<ActivityDay4Binding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            btnCancelAndJoin.setOnClickListener {
                lifecycleScope.launch {
                    val job = launch {
                        repeat(10) { value ->
                            Log.e("kyh!!!", "value : $value")
                            delay(300L)
                        }
                    }
                    Log.e("kyh!!!", "1초 delay 시작")
                    delay(1000L)
                    Log.e("kyh!!!", "1초 delay 끝")
                    job.cancelAndJoin()
                    Log.e("kyh!!!", "작업 완료")
                }
            }
            btnCanNotCancelAndJoin.setOnClickListener {
                val startTime = System.currentTimeMillis()
                lifecycleScope.launch {
                    logCurrentThread("lifecycleScope")
                    val job = launch(Dispatchers.Default) {
                        logCurrentThread("Default")
                        var nextPrintTime = startTime
                        var value = 0
                        while (value < 5) {
                            if (System.currentTimeMillis() >= nextPrintTime) {
                                Log.e("kyh!!!", "value:  ${value++}")
                                nextPrintTime += 1000L
                            }
                        }
                    }
                    Log.e("kyh!!!", "3초 딜레이 시작")
                    delay(3000L) // 중단 포인트
                    Log.e("kyh!!!", "3초 딜레이 끝")
                    job.cancelAndJoin()
                    Log.e("kyh!!!", "작업 완료")
                }
            }

            btnCanCancelAndJoin.setOnClickListener {
                val startTime = System.currentTimeMillis()
                lifecycleScope.launch {
                    logCurrentThread("lifecycleScope")
                    val job = launch(Dispatchers.Default) {
                        logCurrentThread("Default")
                        var nextPrintTime = startTime
                        var value = 0
                        while (value < 5 && isActive) {
                            if (System.currentTimeMillis() >= nextPrintTime) {
                                Log.e("kyh!!!", "value:  ${value++}")
                                nextPrintTime += 1000L
                            }
                        }
                    }
                    Log.e("kyh!!!", "3초 딜레이 시작")
                    delay(3000L) // delay a bit
                    Log.e("kyh!!!", "3초 딜레이 끝")
                    job.cancelAndJoin()
                    Log.e("kyh!!!", "작업 완료")
                }
            }
            btnClosingResource.setOnClickListener {
                lifecycleScope.launch {
                    val job = launch {
                        try {
                            repeat(100) { value ->
                                Log.e("kyh!!!", "value $value")
                                delay(1000L)
                            }
                        } finally {
                            Log.e("kyh!!!", "정리가 필요한 부분 정리")
                        }
                    }
                    delay(3000L)
                    job.cancelAndJoin()
                    Log.e("kyh!!!", "작업 완료")
                }
            }

            btnNonCancellable.setOnClickListener {
                lifecycleScope.launch {
                    val job = launch {
                        try {
                            repeat(100) { value ->
                                Log.e("kyh!!!", "value $value")
                                delay(1000L)
                            }
                        } finally {
                            Log.e("kyh!!!", "정리가 필요한 부분 정리")
                            withContext(NonCancellable) {
                                Log.e("kyh!!!", "NonCancellable Job 작업중")
                                delay(3000L)
                                Log.e("kyh!!!", "3초후 NonCancellable Job 작업 완료")
                            }
                        }
                    }
                    delay(3000L)
                    job.cancelAndJoin()
                    Log.e("kyh!!!", "작업 완료")
                }
            }

            btnWithTimeOut.setOnClickListener {
                lifecycleScope.launch {
                    withTimeout(3000L) {
                        repeat(1000) { value ->
                            Log.e("kyh!!!", "value $value")
                            delay(1000L)
                        }
                    }
                }
            }
        }
    }
}