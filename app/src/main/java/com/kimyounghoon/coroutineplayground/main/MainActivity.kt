package com.kimyounghoon.coroutineplayground.main

import android.content.Intent
import com.kimyounghoon.coroutineplayground.Day8.Day8Activity
import com.kimyounghoon.coroutineplayground.base.MainItemRecyclerActivity
import com.kimyounghoon.coroutineplayground.day1.Day1Activity
import com.kimyounghoon.coroutineplayground.day2.Day2Activity
import com.kimyounghoon.coroutineplayground.day3.Day3Activity
import com.kimyounghoon.coroutineplayground.day4.Day4Activity
import com.kimyounghoon.coroutineplayground.day5.Day5Activity
import com.kimyounghoon.coroutineplayground.day6.Day6Activity
import com.kimyounghoon.coroutineplayground.day7.Day7Activity
import com.kimyounghoon.coroutineplayground.day9.Day9Activity
import com.kimyounghoon.coroutineplayground.model.MainItem

@ExperimentalStdlibApi
class MainActivity : MainItemRecyclerActivity() {
    override fun getRecyclerListener(): (Int) -> Unit = { position ->
        when (position) {
            0 -> {
                Intent(this@MainActivity, Day1Activity::class.java).let {
                    startActivity(it)
                }
            }
            1 -> {
                Intent(this@MainActivity, Day2Activity::class.java).let {
                    startActivity(it)
                }
            }
            2 -> {
                Intent(this@MainActivity, Day3Activity::class.java).let {
                    startActivity(it)
                }
            }
            3 -> {
                Intent(this@MainActivity, Day4Activity::class.java).let {
                    startActivity(it)
                }
            }
            4 -> {
                Intent(this@MainActivity, Day5Activity::class.java).let {
                    startActivity(it)
                }
            }
            5 -> {
                Intent(this@MainActivity, Day6Activity::class.java).let {
                    startActivity(it)
                }
            }
            6 -> {
                Intent(this@MainActivity, Day7Activity::class.java).let {
                    startActivity(it)
                }
            }
            7 ->{
                Intent(this@MainActivity, Day8Activity::class.java).let {
                    startActivity(it)
                }
            }
            8 ->{
                Intent(this@MainActivity, Day9Activity::class.java).let {
                    startActivity(it)
                }
            }
        }
    }

    override fun getMainItems(): List<MainItem> = listOf(
        MainItem(content = "Day1"),
        MainItem(content = "Day2"),
        MainItem(content = "Day3"),
        MainItem(content = "Day4"),
        MainItem(content = "Day5"),
        MainItem(content = "Day6"),
        MainItem(content = "Day7"),
        MainItem(content = "Day8"),
        MainItem(content = "Day9")
    )

}