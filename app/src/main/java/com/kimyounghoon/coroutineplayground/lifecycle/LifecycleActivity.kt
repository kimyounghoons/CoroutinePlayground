package com.kimyounghoon.coroutineplayground.lifecycle

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kimyounghoon.coroutineplayground.R
import com.kimyounghoon.coroutineplayground.base.BaseActivity
import com.kimyounghoon.coroutineplayground.databinding.LifecycleActivityBinding
import com.kimyounghoon.coroutineplayground.extensions.log
import com.kimyounghoon.coroutineplayground.lifecycle.fragment.LifecycleFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class LifecycleActivity : BaseActivity<LifecycleActivityBinding>() {
    var currentJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "LifecycleActivity"
        log("onCreate")
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LifecycleFragment.newInstance())
                .commitNow()
        }

        binding.apply {
            btnLaunchWhenCreated.setOnClickListener {
                currentJob = lifecycleScope.launchWhenCreated {
                    var i = 10
                    while (i > 0 && isActive) {
                        delay(1000L)
                        log("activity lifecycleScope launchWhenCreated : $i")
                        i--
                    }
                }
            }
            btnLaunchWhenStarted.setOnClickListener {
                currentJob = lifecycleScope.launchWhenStarted {
                    var i = 10
                    while (i > 0 && isActive) {
                        delay(1000L)
                        log("activity lifecycleScope launchWhenStarted : $i")
                        i--
                    }
                }
            }
            btnLaunchWhenResumed.setOnClickListener {
                currentJob = lifecycleScope.launchWhenResumed {
                    var i = 10
                    while (i > 0 && isActive) {
                        delay(1000L)
                        log("activity lifecycleScope launchWhenResumed : $i")
                        i--
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        log("activity onStart : currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onResume() {
        super.onResume()
        log("activity onResume :  currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onPause() {
        super.onPause()
        log("activity onPause : currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onStop() {
        super.onStop()
        log("activity onStop : currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("activity onDestroy : currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }
}