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
import cn.isif.reviewandroid.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity(), ServiceConnection {
    private lateinit var myBind: SimpleBindService.MyBind
    private val binding by lazy {
        ActivityServiceBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btStartIntentService.setOnClickListener {
            //启动SimpleIntentService服务
            val i = SimpleIntentService.newIntent(this)
            this.startService(i)
        }
        binding.btBindService.setOnClickListener {
            SimpleService.startService(this)
        }
        binding.btStopService.setOnClickListener {
            SimpleService.stopService(this)
        }
        binding.btBindService.setOnClickListener {
            SimpleBindService.bindService(this, this)
        }
        binding.btUnbindService.setOnClickListener {
            SimpleBindService.unbindService(this, this)
        }
        binding.btStartFrontService.setOnClickListener {
            SimpleFrontService.startService(this)
        }
        binding.btStopFrontService.setOnClickListener {
            SimpleFrontService.stopService(this)
        }
    }

    companion object {
        private val TAG: String = "ServiceActivity"
        fun actionStart(activity: Activity) {
            val intent = Intent(activity, ServiceActivity::class.java)
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
