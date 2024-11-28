package cn.isif.reviewandroid.views

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import cn.isif.reviewandroid.R

class EventActivity : AppCompatActivity() {
    lateinit var root: ConstraintLayout
    lateinit var parent: RelativeLayout
    lateinit var bt1: Button
    lateinit var bt2: Button
    var tagArray = listOf<String>("aa","bb","cc","dd","ee","ff","gg","hh")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        root = findViewById(R.id.root)
        root.apply {
            tag = "root"
            setOnTouchListener(touchListenerObj)
        }
        parent = findViewById(R.id.parent)
        parent.apply {
            tag = "parent"
            setOnTouchListener(touchListenerObj)
        }
        bt1 = findViewById(R.id.bt_1)
        bt1.apply {
            tag = "Button1"
            setOnTouchListener(touchListenerObj)
        }
        bt2 = findViewById(R.id.bt_2)
        bt2.apply {
            tag = "Button2"
            setOnTouchListener(touchListenerObj)
        }
        findViewById<TagsView>(R.id.tags).setTags(tagArray)
        ObjectAnimator.ofInt(bt1, "width", 500).setDuration(5000).start()
    }

    private val touchListenerObj = View.OnTouchListener { v, event ->
        Log.d(TAG, "${v.tag} ---- onTouch ---- ${event?.action}")
        false
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
//        Handler
    }

    companion object {
        final val TAG = "EventActivity"
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, EventActivity::class.java))
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "Activity ---- onTouchEvent ---- ${event?.action}")
        return super.onTouchEvent(event)
    }
}
