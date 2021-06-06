package com.cafe.noteapp.ui.home.list

import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentHomeListBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope

class HomeListFragment : BaseFragment<HomeListViewModel, FragmentHomeListBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home_list

    override val viewModel: HomeListViewModel by getLazyViewModel(ViewModelScope.ACTIVITY)

    override fun onViewInitialized(binding: FragmentHomeListBinding) {
        super.onViewInitialized(binding)

    }


}