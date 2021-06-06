package com.cafe.noteapp.ui.home.list

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentHomeListBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope
import com.cafe.noteapp.ui.home.dialog.NewFolderDialog
import com.cafe.noteapp.util.extentions.findNaveController

class HomeListFragment : BaseFragment<HomeListViewModel, FragmentHomeListBinding>(),
    View.OnClickListener {
    override val layoutId: Int
        get() = R.layout.fragment_home_list

    override val viewModel: HomeListViewModel by getLazyViewModel(ViewModelScope.ACTIVITY)

    override fun onViewInitialized(binding: FragmentHomeListBinding) {
        super.onViewInitialized(binding)
        initView();
    }

    private fun initView() {
        binding.plusBtn.setOnClickListener(this)
        binding.addNoteBtn.setOnClickListener(this)
        binding.addFolderBtn.setOnClickListener(this)
    }

    private fun addFolderDialog() {
        val newFolderDialog: NewFolderDialog = NewFolderDialog(
            onConfirm = {
                viewModel.insertNewFolder(it)
            },
            onCancel = {

            }
        )

        newFolderDialog.show(childFragmentManager, newFolderDialog.tag)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.plusBtn -> {
                binding.addFolderBtn.isVisible = true
                binding.addNoteBtn.isVisible = true
            }

            R.id.addFolderBtn -> {
                addFolderDialog()
            }

            R.id.addNoteBtn -> {
                findNaveController().navigate(HomeListFragmentDirections.actionHomeListFragmentToListDetailFragment())
            }
        }
    }


}