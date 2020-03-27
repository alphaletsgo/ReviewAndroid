package cn.isif.aidlservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AIDLService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate")
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG,"onBind")
        return mBinder
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy")
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG,"onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG,"onRebind")
        super.onRebind(intent)
    }

    companion object{
        private const val TAG = "AIDLService"

        fun startService(context: Context){
            context.startService(Intent(context,AIDLService::class.java))
        }
        fun stopService(context: Context){
            context.stopService(Intent(context,AIDLService::class.java))
        }
    }

    private val mBinder = object:IManager.Stub(){
        override fun add(x: Int, y: Int): Int {
            return x + y
        }

        override fun min(x: Int, y: Int): Int {
            return if (x>y) y else x
        }

    }
}
