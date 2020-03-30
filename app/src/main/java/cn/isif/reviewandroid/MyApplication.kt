package cn.isif.reviewandroid

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.util.Log

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val pid = android.os.Process.myPid()
        val processName:String = getProcessId(this,pid).toString()
        Log.d(TAG, "application is start: {$pid} - {$processName}")

        val process = ProcessBuilder("/system/bin/ping").redirectErrorStream(true).start()

    }

    companion object {
        private const val TAG = "MyApplication"
        fun getProcessId(context: Context, pid: Int):String?{
            val mActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (appProcess in mActivityManager.runningAppProcesses){
                if (pid == appProcess.pid){
                    return appProcess.processName
                }
            }
            return null
        }
    }
}