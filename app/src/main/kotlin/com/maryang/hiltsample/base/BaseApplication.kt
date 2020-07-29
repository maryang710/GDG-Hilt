package com.maryang.hiltsample.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.maryang.hiltsample.event.LogoutEvent
import com.maryang.hiltsample.event.RxBus
import com.maryang.hiltsample.util.ErrorHandler
import com.maryang.hiltsample.util.extension.showToast
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins


@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        lateinit var appContext: Context
        const val TAG = "HileSample"
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Stetho.initializeWithDefaults(this)
        setErrorHanlder()
    }

    @SuppressLint("CheckResult")
    private fun setErrorHanlder() {
        // onError 가 없거나, onError에서 또 Exception이 나면 오는애
        RxJavaPlugins.setErrorHandler {
            ErrorHandler.handle(it)
        }

        RxBus.observe()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is LogoutEvent ->
                        showToast("Logout")
                }
            }
    }
}
