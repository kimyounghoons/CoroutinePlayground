package com.kimyounghoon.coroutineplayground.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding as VB

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = createBindingInstance(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    abstract fun initView()

    @Suppress("UNCHECKED_CAST")
    protected open fun createBindingInstance(inflater: LayoutInflater, container: ViewGroup? = null): VB {
        var type = findParameterizedType(javaClass)
        while (type !is ParameterizedType) {
            type = findParameterizedType(type as Class<*>)
        }

        val vbType = type.actualTypeArguments[0]
        val vbClass = vbType as Class<VB>
        val method = vbClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        return method.invoke(null, inflater, container, false) as VB
    }

    private fun findParameterizedType(javaClass: Class<*>) = javaClass.genericSuperclass

}