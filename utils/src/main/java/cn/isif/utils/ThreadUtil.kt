package cn.isif.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

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
    val mScheduledThread: ScheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() + 1, DefaultThreadFactory())
}

internal class DefaultThreadFactory : ThreadFactory {
    private val group: ThreadGroup
    private val threadNumber: AtomicInteger = AtomicInteger(1)
    private val namePrefix: String

    // 提供创建线程的API。
    override fun newThread(r: Runnable?): Thread {
        // 线程对应的任务是Runnable对象r
        val t = Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0)
        // 设为“非守护线程”
        if (t.isDaemon) t.isDaemon = false
        // 将优先级设为“Thread.NORM_PRIORITY”
        if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
        return t
    }

    companion object {
        private val poolNumber: AtomicInteger = AtomicInteger(1)
    }

    init {
        val s = System.getSecurityManager()
        group = if (s != null) s.threadGroup else Thread.currentThread().threadGroup
        namePrefix = "pool-" +
                poolNumber.getAndIncrement().toString() +
                "-thread-"
    }
}