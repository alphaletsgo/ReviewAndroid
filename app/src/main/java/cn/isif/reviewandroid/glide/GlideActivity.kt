package cn.isif.reviewandroid.glide

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import cn.isif.reviewandroid.R
import com.bumptech.glide.Glide

class GlideActivity : AppCompatActivity() {
    lateinit var image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)
        image = findViewById(R.id.image)
        //GlideApp是注解处理器自动生成的，要使用GlideApp，必须先配置应用的AppGlideModule模块
//        GlideApp.with(this).load("https://gank.io/images/25d3e3db2c1248bb917c09dc4f50a46f").into(image);
        Glide.with(this).load("https://gank.io/images/25d3e3db2c1248bb917c09dc4f50a46f").into(image);
    }

    companion object{
        const val TAG = "GlideActivity"
        fun startActivity(activity: Activity){
            activity.startActivity(Intent(activity,GlideActivity::class.java))
        }
    }
}
