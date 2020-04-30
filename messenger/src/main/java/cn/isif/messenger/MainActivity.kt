package cn.isif.messenger

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import java.util.*

/**
 * 测试Messenger
 * Messenger是基于AIDL实现
 */
class MainActivity : AppCompatActivity() {
    lateinit var btBind: Button
    lateinit var btSend: Button

    lateinit var mService: Messenger
    //该messenger用于接受 service端发送的消息
    var replyMessenger = Messenger(object : Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what){
                MyConstants.MSG_FROM_SERVICE -> {
                    Log.d(TAG,"${msg.data.getString("reply")}");
                }
            }
            super.handleMessage(msg)
        }
    })


    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = Messenger(service)

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btBind = findViewById(R.id.bt_bind)
        btSend = findViewById(R.id.bt_send)
        btBind.setOnClickListener {
            bindService(MessengerService.getActionIntent(this), mConnection, Context.BIND_AUTO_CREATE)
        }
        btSend.setOnClickListener {
            if (mService != null) {
                var msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT)
                var data = Bundle()
                data.putString("msg", "message:${UUID.randomUUID()} , from client.")
//                msg.obj//object对象只能是系统提供的对象，不支持自定义对象
                msg.data = data
                msg.replyTo = replyMessenger //用于接受服务器回传消息
                mService.send(msg)
            }
        }
    }

    override fun onDestroy() {
        unbindService(mConnection)
        super.onDestroy()
    }

    companion object{
        var TAG = "MainActivity"
    }
}
