package com.cafe.noteapp.ui.home.list.adapter

import androidx.databinding.ViewDataBinding

open class MultiLayoutAdapter<T : Any, B : ViewDataBinding>(
    private val layoutId: Int,
    items: List<T> = emptyList(),
    onItemClicked: ((T) -> Unit)? = null,
    onBind: B.(Int) -> Unit = {}
) : BaseAdapter<T, B>(items = items, onItemClicked = onItemClicked, onBind = onBind) {

    override fun getLayoutId(position: Int): Int = layoutId
}