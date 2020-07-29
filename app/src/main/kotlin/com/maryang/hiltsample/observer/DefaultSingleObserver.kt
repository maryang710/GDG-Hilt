package com.maryang.hiltsample.observer

import com.maryang.hiltsample.util.ErrorHandler
import io.reactivex.observers.DisposableSingleObserver


abstract class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onError(e: Throwable) {
        ErrorHandler.handle(e)
    }
}
