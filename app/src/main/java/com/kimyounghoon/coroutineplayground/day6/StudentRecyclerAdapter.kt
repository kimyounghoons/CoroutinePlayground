package com.kimyounghoon.coroutineplayground.day6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kimyounghoon.coroutineplayground.databinding.ViewholderStudentBinding
import com.kimyounghoon.coroutineplayground.model.Student
import kotlin.random.Random

class StudentRecyclerAdapter(val recyclerListener: () -> Unit) : RecyclerView.Adapter<StudentRecyclerAdapter.SampleViewHolder>() {
    private val _items = mutableListOf(
        Student(name = "김영훈" + Random.nextInt(100)),
        Student(name = "김영훈" + Random.nextInt(100)),
        Student(name = "김영훈" + Random.nextInt(100)),
        Student(name = "김영훈" + Random.nextInt(100)),
        Student(name = "김영훈" + Random.nextInt(100)),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SampleViewHolder(ViewholderStudentBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bindStudent(_items[position])
    }

    fun addRandomStudents() {
        val position = _items.size
        listOf(
            Student(name = "김영훈" + Random.nextInt(100)),
            Student(name = "김영훈" + Random.nextInt(100)),
            Student(name = "김영훈" + Random.nextInt(100)),
            Student(name = "김영훈" + Random.nextInt(100)),
            Student(name = "김영훈" + Random.nextInt(100)),
        ).let {
            _items.addAll(it)
            notifyItemInserted(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = _items.size

    inner class SampleViewHolder(val binding: ViewholderStudentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindStudent(student: Student) {
            with(binding) {
                tvStudent.text = student.name
                etNum.setOnFocusChangeListener { view, b ->
                    if (b.not()) {
                        recyclerListener.invoke()
                    }
                }
            }
        }
    }
}