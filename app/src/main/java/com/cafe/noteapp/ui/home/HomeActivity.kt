package com.cafe.noteapp.ui.home

import android.util.Log
import android.view.View
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.ActivityHomeBinding
import com.cafe.noteapp.ui.base.BaseActivity
import com.cafe.noteapp.ui.home.list.ListItem

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {
    override val layoutId: Int = R.layout.activity_home
    override val viewModel: HomeViewModel by getLazyViewModel()

    override fun onViewInitialized(binding: ActivityHomeBinding) {
        super.onViewInitialized(binding)

    }

    fun onMoreClicked(v: View, listItem: ListItem) {
        Log.d("TAG", "onMoreClicked: finally got the callback $listItem ")
    }
}