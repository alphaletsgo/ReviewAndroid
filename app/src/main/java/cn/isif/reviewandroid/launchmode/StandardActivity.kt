package cn.isif.reviewandroid.launchmode

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import cn.isif.reviewandroid.R

class StandardActivity : AppCompatActivity() {
    lateinit var tv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
        setContentView(R.layout.activity_standard)
        tv = findViewById(R.id.text)
        if (savedInstanceState==null){
            tv.text = "halo Kotlin"
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG,"onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRestart() {
        Log.d(TAG,"onRestart")
        super.onRestart()
    }

    override fun onStart() {
        Log.d(TAG,"onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG,"onResume")
        super.onResume()
    }

    companion object{
        private const val TAG = "StandardActivity"
        fun startActivity(context: Context){
            context.startActivity(Intent(context,StandardActivity::class.java))
        }
    }
}
