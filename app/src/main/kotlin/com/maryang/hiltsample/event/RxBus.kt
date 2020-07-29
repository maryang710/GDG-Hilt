package com.maryang.hiltsample.event

import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject

object RxBus {
    private val bus = PublishSubject.create<Event>()

    fun post(event: Event) {
        bus.onNext(event)
    }

    fun observe() =
        bus.toFlowable(BackpressureStrategy.BUFFER)
}
