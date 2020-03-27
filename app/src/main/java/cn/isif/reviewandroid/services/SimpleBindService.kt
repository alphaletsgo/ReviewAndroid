package cn.isif.reviewandroid.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.nfc.Tag
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.text.DecimalFormat

//一个Service必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁。
class SimpleBindService : Service() {

    val myBind:MyBind = MyBind()

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind")
        return myBind
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate，The thread id is " + Thread.currentThread().getId());
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    companion object {
        private const val TAG = "SimpleBindService"
        fun bindService(context: Context,serviceConnection: ServiceConnection){
            val intent = Intent(context,SimpleBindService::class.java)
            context.bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE)
        }

        fun unbindService(context: Context,serviceConnection: ServiceConnection){
            context.unbindService(serviceConnection)
        }
    }

    class MyBind : Binder() {
        fun startDownload(): Unit {
            Log.d("MyBind", "start download")
            Thread(Runnable {
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
            }).start()
        }
    }
}