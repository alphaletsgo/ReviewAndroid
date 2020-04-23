package cn.isif.reviewandroid.components.viewpager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.isif.reviewandroid.R

/**
 * 这里演示Viewpager使用方法和FragmentPagerAdapter与FragmentStatePagerAdapter的区别
 */
class ViewPagerActivity : AppCompatActivity() {
    lateinit var vp:ViewPager   //viewpager
    val arrayStr = arrayOf("page1","page2","page3","page4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        vp = findViewById(R.id.vp)
        //使用FragmentStatePagerAdapter
//        vp.adapter = object : FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
//            override fun getItem(position: Int): Fragment {
//                return FragmentTest.newInstance("ViewPager",arrayStr[position])
//            }
//
//            override fun getCount(): Int {
//                return arrayStr.size
//            }
//        }
        //使用FragmentPagerAdapter
        vp.adapter = object : FragmentPagerAdapter(supportFragmentManager, FragmentPagerAdapter . BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
            override fun getItem(position: Int): Fragment {
                return FragmentTest.newInstance("ViewPager",arrayStr[position])
            }

            override fun getCount(): Int {
                return arrayStr.size
            }
        }
        /**
         * 这里总结一下FragmentStatePagerAdapter与FragmentPagerAdapter的区别
         *  1）FragmentPagerAdapter 不会移除fragment
         *  2）FragmentStatePagerAdapter会缓存Fragment的状态 不用的fragment会被卸载 下次会更加缓存的状态创建
         *  3）一般页面比较少的可以用FragmentPagerAdapter，页面比较多的话推荐使用FragmentStatePagerAdapter
         *
         */
        Log.d(TAG,"onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }

    companion object{
        val TAG:String = "ViewPagerActivity"
        fun startAction(activity:Activity){
            activity.startActivity(Intent(activity,ViewPagerActivity::class.java))
        }
    }

}
