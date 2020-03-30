package cn.isif.reviewandroid.ipc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.isif.reviewandroid.utils.UserManager
import cn.isif.reviewandroid.R

class IPCFirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
        setContentView(R.layout.activity_i_p_c_first)
        UserManager.sUserId = 2
        Log.d(TAG,"Ths UserManger'sUserId is {${UserManager.sUserId.toShort()}}")
    }

    companion object{
        private const val TAG = "IPCFirstActivity"
        fun startActivity(context: Context){
            context.startActivity(Intent(context,IPCFirstActivity::class.java))
        }
    }
}
