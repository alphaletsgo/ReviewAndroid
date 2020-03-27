package cn.isif.reviewandroid.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import cn.isif.reviewandroid.R


class SimpleFrontService : Service() {
    private val CHANNEL_ID = "com.appname.notification.channel"

    /**
     * 通过通知启动服务,针对Android o
     * Android O 在创建通知栏的时候需要指定channel
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setForegroundServiceByO() {
        //设定的通知渠道名称
        val channelName = ""
        //设置通知的重要程度
        val importance = NotificationManager.IMPORTANCE_LOW
        //构建通知渠道
        val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
        //在创建的通知渠道上发送通知
        val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground) //设置通知图标
            setContentTitle("notification Title")//设置通知标题
            setContentText("notification Content")//设置通知内容
            setAutoCancel(true) //用户触摸时，自动关闭
            setOngoing(true);//设置处于运行状态
        }
        //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel);
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(111, builder.build());
    }

    private fun setForegroundService() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground) //设置通知图标
            setContentTitle("notification Title")//设置通知标题
            setContentText("notification Content")//设置通知内容
            setAutoCancel(true) //用户触摸时，自动关闭
            setOngoing(true);//设置处于运行状态
        }
        //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(111, builder.build());
    }

    override fun onCreate() {
        super.onCreate()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setForegroundServiceByO()
        } else {
            setForegroundService()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    companion object {
        private const val TAG = "SimpleFrontService"
        fun startService(context: Context) {
            val intent = Intent(context,SimpleFrontService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }

        fun stopService(context: Context) {
            context.stopService(Intent(context,SimpleFrontService::class.java))
        }
    }
}
