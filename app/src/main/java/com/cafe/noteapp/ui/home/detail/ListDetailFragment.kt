package com.cafe.noteapp.ui.home.detail

import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentListDetailBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope

class ListDetailFragment : BaseFragment<ListDetailViewModel, FragmentListDetailBinding>() {
    override val viewModel: ListDetailViewModel by getLazyViewModel(ViewModelScope.FRAGMENT)
    override val layoutId: Int = R.layout.fragment_list_detail

    override fun onViewInitialized(binding: FragmentListDetailBinding) {
        super.onViewInitialized(binding)
        binding.listDetailTitle.text = "This is a sample Text"

    }
}