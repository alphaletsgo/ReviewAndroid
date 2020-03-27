package cn.isif.reviewandroid.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

class SimpleService : Service() {

    override fun onCreate() {
        Log.d(TAG, "onCreate，The thread id is " + Thread.currentThread().getId());
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        //耗时任务

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind")
        return null
    }

    companion object {
        private val TAG = "SimpleService"
        fun startService(context: Context){
            val intent = Intent(context,SimpleService::class.java)
            context.startService(intent)
        }
        fun stopService(context: Context){
            context.stopService(Intent(context,SimpleService::class.java))
        }
    }

}
