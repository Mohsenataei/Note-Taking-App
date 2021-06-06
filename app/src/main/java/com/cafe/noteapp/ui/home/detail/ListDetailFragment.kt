package com.cafe.noteapp.ui.home.detail

import com.cafe.data.source.db.dao.NoteDao
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.FragmentListDetailBinding
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.ViewModelScope
import javax.inject.Inject

class ListDetailFragment @Inject constructor(
    private val noteDao: NoteDao
) : BaseFragment<ListDetailViewModel, FragmentListDetailBinding>() {
    override val viewModel: ListDetailViewModel by getLazyViewModel(ViewModelScope.FRAGMENT)
    override val layoutId: Int = R.layout.fragment_list_detail
}