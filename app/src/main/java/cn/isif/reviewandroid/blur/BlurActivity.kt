package cn.isif.reviewandroid.blur

import android.content.Context
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import cn.isif.reviewandroid.R

class BlurActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur)
        setBlur()
    }

    private fun setBlur() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            findViewById<CardView>(R.id.card)?.apply {
                setRenderEffect(RenderEffect.createBlurEffect(30f, 30f, Shader.TileMode.REPEAT))
            }
        }
    }

    companion object {
        fun startActivity(context: Context): Unit {
            context.startActivity(Intent(context, BlurActivity::class.java))
        }
    }
}