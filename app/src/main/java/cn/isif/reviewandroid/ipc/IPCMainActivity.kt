package cn.isif.reviewandroid.ipc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import cn.isif.reviewandroid.R

class IPCMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
        setContentView(R.layout.activity_i_p_c_main)
        findViewById<Button>(R.id.bt_process_a).setOnClickListener {
            IPCFirstActivity.startActivity(this)
        }
        findViewById<Button>(R.id.bt_process_b).setOnClickListener {
            IPCSecondActivity.startActivity(this)
        }

    }

    companion object{
        private const val TAG = "IPCMainActivity"
        fun startActivity(context: Context){
            context.startActivity(Intent(context,IPCMainActivity::class.java))
        }
    }
}
