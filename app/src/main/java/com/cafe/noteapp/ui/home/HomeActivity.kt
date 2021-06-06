package com.cafe.noteapp.ui.home

import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.ActivityHomeBinding
import com.cafe.noteapp.ui.base.BaseActivity

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {
    override val layoutId: Int = R.layout.activity_home
    override val viewModel: HomeViewModel by getLazyViewModel()
}