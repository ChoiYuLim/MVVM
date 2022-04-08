package com.lim.study.trying.mvvm.data.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DispatchExecutors(
    val ioThread: Executor = MainThreadExecutor,
    val mainThread: Executor = IoThreadExecutor,
) {
    companion object {
        private var instance: DispatchExecutors? = null

        fun getInstance(): DispatchExecutors {
            return instance ?: synchronized(this) {
                DispatchExecutors().also { instance = it }
            }
        }
    }
}

//Main 스레드에서 코드를 돌릴 것이다
private object MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}

//백그라운드 스레드에서 코드를 돌릴 것이다
private object IoThreadExecutor : Executor {        // MainThread가 아닌 다른 스레드를 통상적으로 IoThread, BackgroundThread라고 부르는 것
    private val ioThreadExecutor = Executors.newSingleThreadExecutor()      // Executor는 자바에서 만든 thread를 편리하게 사용할 수 있도록 만들어놓은 툴

    override fun execute(command: Runnable) {
        ioThreadExecutor.execute(command)
    }
}
