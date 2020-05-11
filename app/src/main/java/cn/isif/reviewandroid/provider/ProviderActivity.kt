package cn.isif.reviewandroid.provider

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.isif.reviewandroid.R

class ProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)
        //测试provider
        var uri = Uri.parse("content://cn.isif.reviewandroid.book.provider/book")
        var cursor = contentResolver.query(uri,null,null,null,null);
        if (cursor != null) {
            while (cursor.moveToNext()){
                Log.d(TAG,"[name:${cursor.getString(0)} ,price:${cursor.getDouble(1)}, uid:${cursor.getInt(2)}]")
            }
        }
    }

    companion object{
        const val TAG = "ProviderActivity"
        fun startActivity(activity: Activity){
            activity.startActivity(Intent(activity,ProviderActivity::class.java))
        }
    }
}
