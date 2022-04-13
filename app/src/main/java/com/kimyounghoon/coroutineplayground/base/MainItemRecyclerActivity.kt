package com.kimyounghoon.coroutineplayground.base

import androidx.recyclerview.widget.GridLayoutManager
import com.kimyounghoon.coroutineplayground.databinding.ActivityMainBinding
import com.kimyounghoon.coroutineplayground.main.MainRecyclerAdapter
import com.kimyounghoon.coroutineplayground.model.MainItem

abstract class MainItemRecyclerActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var _adapter: MainRecyclerAdapter

    abstract fun getRecyclerListener(): (Int) -> Unit
    abstract fun getMainItems(): List<MainItem>

    override fun initView() {
        with(binding) {
            _adapter = MainRecyclerAdapter(getRecyclerListener())
            rvMain.adapter = _adapter
            rvMain.layoutManager = GridLayoutManager(this@MainItemRecyclerActivity, 3)
            _adapter.setItems(getMainItems())
        }
    }
}