package cn.isif.reviewandroid.launchmode

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.isif.reviewandroid.R

class SingleTopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_top)
    }
    companion object{
        private const val TAG = "SingleTopActivity"
        fun startActivity(context: Context){
            context.startActivity(Intent(context,SingleTopActivity::class.java))
        }
    }
}
