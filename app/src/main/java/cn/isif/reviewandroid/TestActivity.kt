package cn.isif.reviewandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
    companion object{
        fun startActivity(activity:Activity){
            activity.startActivity(Intent(activity,TestActivity::class.java))
        }
    }
}