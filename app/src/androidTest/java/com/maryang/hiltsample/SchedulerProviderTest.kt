package com.maryang.hiltsample

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maryang.hiltsample.hilt.SchedulerProviderModule
import com.maryang.hiltsample.ui.repos.GithubReposViewModel
import com.maryang.hiltsample.util.provider.SchedulerProviderInterface
import com.maryang.hiltsample.util.provider.TestSchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(SchedulerProviderModule::class)
@RunWith(AndroidJUnit4::class)
class SchedulerProviderTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(ApplicationComponent::class)
    abstract class TestSchedulerProviderModule {
        @Binds
        abstract fun bindSchedulerProvider(
            schedulerProvider: TestSchedulerProvider
        ): SchedulerProviderInterface
    }

    @Inject
    lateinit var schedulerProvider: SchedulerProviderInterface

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun searchTest() {
        Log.d("SchedulerProviderTest", "scheduler provider name: ${schedulerProvider::class.java.simpleName}")
        Assert.assertTrue(true)
    }
}
