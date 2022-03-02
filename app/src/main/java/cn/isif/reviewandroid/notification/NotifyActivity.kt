package cn.isif.reviewandroid.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cn.isif.reviewandroid.R
import cn.isif.reviewandroid.TestActivity
import kotlinx.android.synthetic.main.activity_notify.*

class NotifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)
        bt_simple_notify.setOnClickListener {
            simpleNotify("®simple notify title", "simple notify content")
        }
        bt_style_notify.setOnClickListener {
            styleNotify()
        }
    }

    private fun styleNotify() {
        val fullScreenIntent = Intent(this, TestActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val action1 = NotificationCompat.Action.Builder(
            R.drawable.ic_person_black,
            "button1",
            fullScreenPendingIntent
        ).build()

        val action2 = NotificationCompat.Action.Builder(
            R.drawable.ic_person_black,
            "button2",
            fullScreenPendingIntent
        ).build()

        val styleData = MockDatabase.getStyleData()
        val notificationChannelId = NotificationUtil.createNotificationChannel(this, styleData)
        var builder = NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) //设置icon
            .setContentTitle(styleData.contentTitle)
            .setContentText(styleData.contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH) //通知优先级
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setCategory(Notification.CATEGORY_REMINDER)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .addAction(action1)
            .addAction(action2)

        with(NotificationManagerCompat.from(this)) {
            notify(SIMPLE_NOTIFY_ID, builder.build())
        }
    }

    private fun simpleNotify(notifyTitle: String, notifyContextText: String) {
        val fullScreenIntent = Intent(this, TestActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) //设置icon
            .setContentTitle(notifyTitle)
            .setContentText(notifyContextText)
            .setPriority(NotificationCompat.PRIORITY_HIGH) //通知优先级
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setTicker("悬浮通知")
            .setFullScreenIntent(fullScreenPendingIntent, true)

        createNotificationChannel()

        with(NotificationManagerCompat.from(this)) {
            notify(SIMPLE_NOTIFY_ID, builder.build())
        }
    }

    /**
     * 创建渠道 需要在Android 8.0以上
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Test",
                NotificationManager.IMPORTANCE_DEFAULT //渠道的重要性
            ).apply {
                description = "description with notify"
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "notify_channel"
        const val SIMPLE_NOTIFY_ID: Int = 100;

        fun startActivity(context: Context?) {
            context?.apply {
                startActivity(Intent(this, NotifyActivity::class.java))
            }
        }
    }
}