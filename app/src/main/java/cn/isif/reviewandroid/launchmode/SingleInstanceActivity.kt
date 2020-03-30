package cn.isif.reviewandroid.launchmode

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.isif.reviewandroid.R

class SingleInstanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_instance)
    }

    companion object{
        private const val TAG = "SingleInstanceActivity"
        fun startActivity(context: Context){
            context.startActivity(Intent(context,SingleInstanceActivity::class.java))
        }
    }
}
