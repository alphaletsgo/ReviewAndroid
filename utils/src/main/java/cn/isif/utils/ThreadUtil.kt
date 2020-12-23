package cn.isif.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

fun ui(delayMillis: Long = 0, runnable: () -> Unit) {
    if (delayMillis > 0) {
        ThreadUtil.mHandler.postDelayed(runnable, delayMillis)
    } else {
        ThreadUtil.mHandler.post(runnable)
    }
}

fun io(delayMillis: Long = 0, runnable: () -> Unit) {
    if (delayMillis > 0) {
        ThreadUtil.mScheduledThread.schedule(runnable, delayMillis, TimeUnit.MILLISECONDS)
    } else {
        ThreadUtil.mScheduledThread.execute(runnable)
    }
}

object ThreadUtil {
    var mHandler = Handler(Looper.getMainLooper())
    val mScheduledThread: ScheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() + 1)
}

