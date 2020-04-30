package cn.isif.aidl

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var btBind: Button
    lateinit var btUnbind: Button

    lateinit var bmi: IBookManagerInterface

    //处理aidl发来的消息
    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MyContact.MESSAGE_NEW_BOOK_ARRIVED -> {
                    Log.d(TAG, "receive new book : ${msg.obj.toString()}")
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    //实现IOnNewBookArrivedListener.Stub 即Binder
    val newBookListener = object : IOnNewBookArrivedListener.Stub() {
        override fun onNewBookArrived(newBook: Book?) {
            if (newBook != null) {
                handler.obtainMessage(MyContact.MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget()
            }
        }
    }
    //ServiceConnection
    val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bmi = IBookManagerInterface.Stub.asInterface(service)
            bmi.registerListener(newBookListener)//注册监听
//            bmi.bookList()//配合sevice测试ANR
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btBind = findViewById(R.id.bt_bind)
        btUnbind = findViewById(R.id.bt_unbind)
        btBind.setOnClickListener {
            //绑定服务
            bindService(BookManagerService.getActionIntent(this), conn, Context.BIND_AUTO_CREATE)
        }
        btUnbind.setOnClickListener {
            if (bmi!=null){
                //取消监听
                bmi.unregisterListener(newBookListener)
                //解绑
                unbindService(conn)
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
