package cn.isif.reviewandroid.notification

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import java.lang.reflect.Method

object StatusBarUtils {
    private const val STATUS_BAR_SERVICE = "statusbar"

    @SuppressLint("WrongConstant")
    fun collapseStatusBar(context: Context?) {
        context?.let {
            kotlin.runCatching {
                val statusBarManager = it.getSystemService(STATUS_BAR_SERVICE)
                var collapse: Method? = null
                if (Build.VERSION.SDK_INT <= 16) {
                    collapse = statusBarManager.javaClass.getMethod("collapse")
                } else {
                    collapse = statusBarManager.javaClass.getMethod("collapsePanels")
                }
                collapse.isAccessible = true
                collapse.invoke(statusBarManager)
            }.onFailure {
                // 关闭失败
            }
        }
    }
}