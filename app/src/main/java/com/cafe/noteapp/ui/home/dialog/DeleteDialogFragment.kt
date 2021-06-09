package com.cafe.noteapp.ui.home.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.cafe.noteapp.R
import com.cafe.noteapp.databinding.DialogDeleteBinding
import kotlinx.android.synthetic.main.dialog_delete.*
import java.util.*

const val DIALOG_TAG = "DialogTag"

class DeleteDialogFragment(
    val title: String,
    val message: String,
    val onConfirm: (() -> Unit)? = null,
    var onCancel: (() -> Unit)? = null
) : DialogFragment(), View.OnClickListener {


    companion object {
        val singleInstanceTags by lazy { listOf(DIALOG_TAG) }
        val onConfirms by lazy { LinkedList<() -> Unit>() }
        val onCancels by lazy { LinkedList<() -> Unit>() }
        var isShowing = false

        fun newInstance(
            title: String,
            message: String,
            onConfirm: (() -> Unit)? = null,
            onCancel: (() -> Unit)? = null
        ) = DeleteDialogFragment(
            title,
            message,
            onConfirm,
            onCancel
        )
    }
    var accepted = false
    override fun show(manager: FragmentManager, tag: String?) {
        onConfirm?.let { onConfirms.add(it) }
        onCancel?.let { onCancels.add(it) }
        if (singleInstanceTags.contains(tag) && isShowing)
            return
        super.show(manager, tag)

        isShowing = true;
    }

    private lateinit var binding: DialogDeleteBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initialize binding
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_delete, container, false)
        binding.lifecycleOwner = this

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = title
        binding.desctiption.text = message

        binding.confirm.setOnClickListener(this)
        binding.cancel.setOnClickListener(this)
    }

    private fun notifyClick() {
        onCancels.clear()
        onCancel = null
        if (singleInstanceTags.contains(tag))
            while (onConfirms.isNotEmpty())
                onConfirms.pop().invoke()
        else
            onConfirm?.invoke()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        val window = dialog!!.window!!
        window.setGravity(Gravity.CENTER)
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun notifyCancel() {
        onConfirms.clear()
        onCancel = null
        if (singleInstanceTags.contains(tag))
            while (onCancels.isNotEmpty())
                onCancels.pop().invoke()
        else
            onCancel?.invoke()
    }

    override fun onDismiss(dialog: DialogInterface) {
        isShowing = false
        super.onDismiss(dialog)
        if (!accepted)
            notifyCancel()
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.confirm -> {
//                onConfirm?.invoke()
                accepted = true
                notifyClick()
                dismiss()
            }
            R.id.cancel -> {
                onCancel?.invoke()
            }
        }
        dismiss()
    }
}