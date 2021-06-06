package com.cafe.noteapp.ui.home.detail

import android.view.View
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentListDetailBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope
import com.cafe.noteapp.util.extentions.findNaveController

class ListDetailFragment : BaseFragment<ListDetailViewModel, FragmentListDetailBinding>(),
    View.OnClickListener {
    override val viewModel: ListDetailViewModel by getLazyViewModel(ViewModelScope.FRAGMENT)
    override val layoutId: Int = R.layout.fragment_list_detail

    override fun onViewInitialized(binding: FragmentListDetailBinding) {
        super.onViewInitialized(binding)
        initClicks()
        binding.listDetailTitle.text = "This is a sample Text"
    }

    private fun initClicks() {
        binding.listDetailBackBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.listDetailBackBtn -> {
                findNaveController().popBackStack()
            }
        }
    }
}