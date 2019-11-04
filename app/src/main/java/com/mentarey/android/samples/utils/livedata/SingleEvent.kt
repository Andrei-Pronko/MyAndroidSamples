package com.mentarey.android.samples.utils.livedata

import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleEvent<out T>(private val value: T) {

    private val mPending = AtomicBoolean(true)

    val actualValue: T?
        get() {
            return when (mPending.compareAndSet(true, false)) {
                true -> value
                else -> null
            }
        }
}

class SingleEventObserver<T>(private val eventWithValue: (T) -> Unit) : Observer<SingleEvent<T>> {
    override fun onChanged(event: SingleEvent<T>?) {
        event?.actualValue?.let { eventWithValue(it) }
    }
}