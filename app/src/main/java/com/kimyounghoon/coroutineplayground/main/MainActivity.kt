package com.kimyounghoon.coroutineplayground.main

import android.content.Intent
import android.os.Bundle
import com.kimyounghoon.coroutineplayground.base.BaseActivity
import com.kimyounghoon.coroutineplayground.databinding.ActivityMainBinding
import com.kimyounghoon.coroutineplayground.day1.Day1Activity
import com.kimyounghoon.coroutineplayground.day2.Day2Activity
import com.kimyounghoon.coroutineplayground.day3.Day3Activity
import com.kimyounghoon.coroutineplayground.day4.Day4Activity
import com.kimyounghoon.coroutineplayground.day5.Day5Activity
import com.kimyounghoon.coroutineplayground.day6.Day6Activity
import kotlinx.coroutines.DelicateCoroutinesApi


class MainActivity : BaseActivity<ActivityMainBinding>() {

    @OptIn(ExperimentalStdlibApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            btnDay1.setOnClickListener {
                Intent(this@MainActivity, Day1Activity::class.java).let {
                    startActivity(it)
                }
            }
            btnDay2.setOnClickListener {
                Intent(this@MainActivity, Day2Activity::class.java).let {
                    startActivity(it)
                }
            }
            btnDay3.setOnClickListener {
                Intent(this@MainActivity, Day3Activity::class.java).let {
                    startActivity(it)
                }
            }
            btnDay4.setOnClickListener {
                Intent(this@MainActivity, Day4Activity::class.java).let {
                    startActivity(it)
                }
            }
            btnDay5.setOnClickListener {
                Intent(this@MainActivity, Day5Activity::class.java).let {
                    startActivity(it)
                }
            }
            btnDay6.setOnClickListener {
                Intent(this@MainActivity, Day6Activity::class.java).let {
                    startActivity(it)
                }
            }
        }
    }

}