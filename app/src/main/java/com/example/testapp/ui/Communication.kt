package com.example.testapp.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communication<T> : Observable<T>, Mapper<T> {

    abstract class Base<T> : Communication<T> {

        private val liveData = MutableLiveData<T>()
        override fun map(data: T) {
            liveData.value = data!!
        }

        override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(lifecycleOwner, observer)
        }
    }
}

interface Observable<T> {

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>)
}

interface Mapper<T> {

    fun map(data: T)
}