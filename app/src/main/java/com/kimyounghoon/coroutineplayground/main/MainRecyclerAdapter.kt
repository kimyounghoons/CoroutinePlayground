package com.kimyounghoon.coroutineplayground.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kimyounghoon.coroutineplayground.databinding.ViewholderMainBinding
import com.kimyounghoon.coroutineplayground.model.MainItem

class MainRecyclerAdapter(private val recyclerListener: (Int) -> Unit) : RecyclerView.Adapter<MainRecyclerAdapter.MainItemViewHolder>() {
    private val _items = mutableListOf<MainItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MainItemViewHolder(ViewholderMainBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.binding.apply {
            item = _items[position]
            btnDay.setOnClickListener {
                recyclerListener.invoke(position)
            }
            executePendingBindings()
        }
    }

    fun setItems(items: List<MainItem>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = _items.size

    inner class MainItemViewHolder(val binding: ViewholderMainBinding) : RecyclerView.ViewHolder(binding.root)

}