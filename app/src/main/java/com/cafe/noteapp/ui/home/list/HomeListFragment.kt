package com.cafe.noteapp.ui.home.list

import android.widget.Toast
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentHomeListBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope
import com.cafe.noteapp.ui.home.dialog.NewFolderDialog

class HomeListFragment : BaseFragment<HomeListViewModel, FragmentHomeListBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home_list

    override val viewModel: HomeListViewModel by getLazyViewModel(ViewModelScope.ACTIVITY)

    override fun onViewInitialized(binding: FragmentHomeListBinding) {
        super.onViewInitialized(binding)
        binding.plusBtn.setOnClickListener {
            val dialog = NewFolderDialog(onConfirm = {
                Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
            },
                onCancel = {

                }
            )

            dialog.show(childFragmentManager, dialog.tag)
        }

    }


}