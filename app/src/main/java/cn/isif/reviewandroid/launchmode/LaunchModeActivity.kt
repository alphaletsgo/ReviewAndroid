package cn.isif.reviewandroid.launchmode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cn.isif.reviewandroid.R


class LaunchModeActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var bt_standard:Button
    lateinit var bt_singleTask:Button
    lateinit var bt_singleTop:Button
    lateinit var bt_singleInstance:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_mode)

        bt_standard = findViewById(R.id.bt_launch_standard)
        bt_standard.setOnClickListener(this)
        bt_singleTask = findViewById(R.id.bt_launch_singleTask)
        bt_singleTask.setOnClickListener(this)
        bt_singleTop = findViewById(R.id.bt_launch_singleTop)
        bt_singleTop.setOnClickListener(this)
        bt_singleInstance = findViewById(R.id.bt_launch_singleInstance)
        bt_singleInstance.setOnClickListener(this)
    }

    companion object{
        private const val TAG = "LaunchModeActivity"

        fun startActivity(context: Context):Unit{
            context.startActivity(Intent(context,LaunchModeActivity::class.java))
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_launch_standard -> {
//                StandardActivity.startActivity(applicationContext) //这里使用了applicationContext，会报错，原因:当启动模式时standard的时候，该activity会放在启动它的activity所在的栈中，四大组件中只有activity才有任务栈
                StandardActivity.startActivity(this)
            }
            R.id.bt_launch_singleTop -> SingleTopActivity.startActivity(this)
            R.id.bt_launch_singleTask -> SingleTaskActivity.startActivity(this)
            R.id.bt_launch_singleInstance -> SingleInstanceActivity.startActivity(this)
            else -> return
        }
    }

}
