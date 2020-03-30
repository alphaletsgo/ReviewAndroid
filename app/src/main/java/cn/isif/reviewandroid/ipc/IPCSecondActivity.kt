package cn.isif.reviewandroid.ipc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.isif.reviewandroid.R
import cn.isif.reviewandroid.utils.UserManager

class IPCSecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
        setContentView(R.layout.activity_i_p_c_second)
        Log.d(TAG,"Ths UserManger'sUserId is {${UserManager.sUserId.toShort()}}")
    }

    companion object{
        private const val TAG = "IPCSecondActivity"
        fun startActivity(context: Context){
            context.startActivity(Intent(context,IPCSecondActivity::class.java))
        }
    }
}
