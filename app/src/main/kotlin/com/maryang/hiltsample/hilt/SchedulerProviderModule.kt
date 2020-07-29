package com.maryang.hiltsample.hilt

import com.maryang.hiltsample.util.provider.SchedulerProvider
import com.maryang.hiltsample.util.provider.SchedulerProviderInterface
import com.maryang.hiltsample.util.provider.TestSchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class SchedulerProviderModule {

    @Binds
    abstract fun bindSchedulerProvider(
        schedulerProvider: SchedulerProvider
    ): SchedulerProviderInterface
}
