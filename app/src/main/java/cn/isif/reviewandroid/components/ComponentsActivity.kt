package cn.isif.reviewandroid.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import cn.isif.reviewandroid.R
import cn.isif.reviewandroid.components.viewpager.ViewPagerActivity

class ComponentsActivity : AppCompatActivity() {
    lateinit var viewpager: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_components)
        viewpager = findViewById(R.id.cp_viewpager)
        viewpager.setOnClickListener {
            ViewPagerActivity.startAction(this)
        }
    }
    companion object{
        final val TAG = "ComponentsActivity"
        fun startAction(activity: Activity){
            activity.startActivity(Intent(activity,ComponentsActivity::class.java))
        }
    }
}
