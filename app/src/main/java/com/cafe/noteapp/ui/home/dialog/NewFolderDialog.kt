package com.cafe.noteapp.ui.home.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.DialogNewFolderBinding


class NewFolderDialog(
    val onConfirm: (FolderItem) -> Unit,
    val onCancel: () -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogNewFolderBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // initialize binding
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_new_folder, container, false)
        binding.lifecycleOwner = this

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirm.setOnClickListener {
            val folderName = binding.newFolderTitleEd.text.toString()

            if (folderName.isNullOrEmpty())
                Toast.makeText(requireContext(), "لطفا نام پوشه را وارد کنید", Toast.LENGTH_SHORT)
                    .show()
            else {
                this.dismiss()
                onConfirm.invoke(FolderItem(0, folderName, 0))
            }
        }
        binding.cancel.setOnClickListener {
            onCancel.invoke()
            this.dismiss()
        }

    }
}


