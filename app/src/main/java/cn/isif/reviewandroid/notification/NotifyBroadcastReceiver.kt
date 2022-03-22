package cn.isif.reviewandroid.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import cn.isif.reviewandroid.TestActivity

class NotifyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            if (OPEN_ACTIVITY == intent?.action){
                val instantIntent = Intent(context, TestActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                it.startActivity(instantIntent)
                StatusBarUtils.collapseStatusBar(it)
            }
        }
    }

    fun registerReceiver(context: Context?) {
        context?.let {
            val intentFilter = IntentFilter().apply {
                addAction(OPEN_ACTIVITY)
            }
            it.registerReceiver(this, intentFilter)
        }
    }

    fun unRegisterReceiver(context: Context?) {
        context?.let {
            context.unregisterReceiver(this)
        }
    }

    companion object {
        const val OPEN_ACTIVITY = "cn.isif.NOTIFY_OPEN_ACTIVITY"

        fun getBroadcastIntent():Intent {
            return Intent(OPEN_ACTIVITY)
        }
    }
}