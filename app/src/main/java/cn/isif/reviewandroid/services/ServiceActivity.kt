package cn.isif.reviewandroid.services

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import cn.isif.reviewandroid.R
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity(),ServiceConnection {
    lateinit var myBind: SimpleBindService.MyBind

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        bt_start_intent_service.setOnClickListener {
            //启动SimpleIntentService服务
            val i = SimpleIntentService.newIntent(this)
            this.startService(i)
        }
        bt_start_service.setOnClickListener {
            SimpleService.startService(this)
        }
        bt_stop_service.setOnClickListener {
            SimpleService.stopService(this)
        }
        bt_bind_service.setOnClickListener {
            SimpleBindService.bindService(this,this)
        }
        bt_unbind_service.setOnClickListener {
            SimpleBindService.unbindService(this,this)
        }
        bt_start_front_service.setOnClickListener {
            SimpleFrontService.startService(this)
        }
        bt_stop_front_service.setOnClickListener {
            SimpleFrontService.stopService(this)
        }
    }
    companion object{
        private val TAG:String = "ServiceActivity"
        fun actionStart(activity:Activity){
            val intent = Intent(activity,ServiceActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {

    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        myBind = service as SimpleBindService.MyBind
        myBind.startDownload()
    }
}
