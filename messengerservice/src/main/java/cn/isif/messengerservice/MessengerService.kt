package cn.isif.messengerservice

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast

class MessengerService:Service(){
    lateinit var mContext: Context
    private val handler = object: Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what){
                MyConstants.MSG_FROM_CLIENT -> {
                    var bundle = msg.data
                    var mm = bundle.getString("msg")
                    Toast.makeText(mContext,mm,Toast.LENGTH_SHORT).show()

                    //给客户端回复一条消息
                    var client = msg.replyTo //获取客户端的messenger
                    var message = Message.obtain(null,MyConstants.MSG_FROM_SERVICE);
                    message.data = Bundle().apply {
                        putString("reply","yep, I got it.")
                    }
                    client.send(message)
                }
            }
            super.handleMessage(msg)
        }
    }

    private val message = Messenger(handler);

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    override fun onBind(intent: Intent?): IBinder? {
        return message.binder
    }

    companion object{
        const val TAG = "MessengerService"
        fun getActionIntent(activity:Activity):Intent{
            return Intent(activity,MessengerService::class.java)
        }
    }
}