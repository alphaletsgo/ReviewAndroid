package cn.isif.reviewandroid.launchmode

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.isif.reviewandroid.R

class SingleTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_task)
    }

    companion object{
        private const val TAG = "SingleTaskActivity"
        fun startActivity(context: Context){
            context.startActivity(Intent(context,SingleTaskActivity::class.java))
        }
    }
}
