package com.maryang.hiltsample.util.provider

import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject

class TestSchedulerProvider @Inject constructor() : SchedulerProviderInterface {

    val testScheduler = TestScheduler()

    override fun io() =
        testScheduler

    override fun computation() =
        testScheduler

    override fun main() =
        testScheduler
}
