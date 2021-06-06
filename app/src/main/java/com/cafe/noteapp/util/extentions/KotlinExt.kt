package com.cafe.noteapp.util.extentions

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Observe [LiveData] on an instance of [LifecycleOwner] like [Fragment] or [Activity] and observer
 * will be invoked  only if emitted value is not null.
 *
 * @param lifecycleOwner
 * @param observer a lambda function that receives a nonnull [T] and will be invoked when data is available
 */
fun <T> LiveData<T>.observeSafe(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer { if (it != null) observer.invoke(it) })
}