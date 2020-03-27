package cn.isif.reviewandroid.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

import java.text.DecimalFormat
import kotlin.math.log

class SimpleIntentService : IntentService(TAG) {

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent，The thread id is " + Thread.currentThread().getId());
        //这里执行耗时操作
        var i = 0
        Log.d(TAG, "Task start")
        val fo = DecimalFormat(".00")
        while (i < 5) {
            i++
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            Log.d(TAG, "进度：" + fo.format((i / 5f * 100).toDouble()) + "%")
        }
        Log.d(TAG, "Task finished")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }

    companion object {
        private const val TAG = "SimpleIntentService"

        fun newIntent(context: Context): Intent {
            return Intent(context, SimpleIntentService::class.java)
        }
    }
}
