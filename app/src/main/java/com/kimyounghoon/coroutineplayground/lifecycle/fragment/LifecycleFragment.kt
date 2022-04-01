package com.kimyounghoon.coroutineplayground.lifecycle.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.kimyounghoon.coroutineplayground.base.BaseFragment
import com.kimyounghoon.coroutineplayground.databinding.LifecycleFragmentBinding
import com.kimyounghoon.coroutineplayground.extensions.log
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class LifecycleFragment : BaseFragment<LifecycleFragmentBinding>() {
    var currentJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        log("fragment onStart : currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onResume() {
        super.onResume()
        log("fragment onResume :  currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onPause() {
        super.onPause()
        log("fragment onPause : currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onStop() {
        super.onStop()
        log("fragment onStop : currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("fragment onDestroyView : currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("fragment onDestroy : currentJob isActive : ${currentJob?.isActive} isCancelled : ${currentJob?.isCancelled}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLaunchWhenCreated.setOnClickListener {
                currentJob = lifecycleScope.launchWhenCreated {
                    var i = 10
                    while (i > 0 && isActive) {
                        delay(1000L)
                        log("Fragment lifecycleScope launchWhenCreated : $i")
                        i--
                    }
                }
            }
            btnLaunchWhenStarted.setOnClickListener {
                currentJob = lifecycleScope.launchWhenStarted {
                    var i = 10
                    while (i > 0 && isActive) {
                        delay(1000L)
                        log("Fragment lifecycleScope launchWhenStarted : $i")
                        i--
                    }
                }
            }
            btnLaunchWhenResumed.setOnClickListener {
                currentJob = lifecycleScope.launchWhenResumed {
                    var i = 10
                    while (i > 0 && isActive) {
                        delay(1000L)
                        log("Fragment lifecycleScope launchWhenResumed : $i")
                        i--
                    }
                }
            }

            btnViewLifeCycleLaunchWhenCreated.setOnClickListener {
                currentJob = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    var i = 10
                    while (i > 0 && isActive) {
                        delay(1000L)
                        log("fragment viewLifecycle lifecycleScope LaunchWhenCreated : $i")
                        i--
                    }
                }
            }

            btnViewLifeCycleLaunchWhenStarted.setOnClickListener {
                currentJob = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    var i = 10
                    while (i > 0 && isActive) {
                        delay(1000L)
                        log("fragment viewLifecycle lifecycleScope launchWhenStarted : $i")
                        i--
                    }
                }
            }

            btnViewLifeCycleLaunchWhenResumed.setOnClickListener {
                currentJob = viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                    var i = 10
                    while (i > 0 && isActive) {
                        delay(1000L)
                        log("fragment viewLifecycle lifecycleScope launchWhenResumed : $i")
                        i--
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = LifecycleFragment()
    }

}