package cn.isif.reviewandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.isif.reviewandroid.components.ComponentsActivity
import cn.isif.reviewandroid.glide.GlideActivity
import cn.isif.reviewandroid.hook.HookActivity
import cn.isif.reviewandroid.provider.ProviderActivity
import cn.isif.reviewandroid.ipc.IPCMainActivity
import cn.isif.reviewandroid.launchmode.LaunchModeActivity
import cn.isif.reviewandroid.notification.NotifyActivity
import cn.isif.reviewandroid.permission.PermissionActivity
import cn.isif.reviewandroid.services.ServiceActivity
import cn.isif.reviewandroid.views.EventActivity
import cn.isif.reviewandroid.web.WebActivity

import kotlinx.android.synthetic.main.activity_main.*

/**
 * 旋转屏幕时activity的生命周期：
 * onPause
 * onStop
 * onSaveInstanceState
 * onDestroy
 * onCreate
 * onStart
 * onRestoreInstanceState
 * onResume
 */
class MainActivity : AppCompatActivity(), OnItemClickListener {
    private val items = arrayOf(
        "启动模式",
        "IPC",
        "View",
        "动画",
        "数据库",
        "服务",
        "组件",
        "ContentProvider",
        "Event",
        "Glide",
        "Hook",
        "Permission",
        "Web",
        "通知"
    )
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //recyclerView列表点击处理
    override fun onItemClick(v: View, position: Int) {
        when (items[position]) {
            "服务" -> ServiceActivity.actionStart(this)
            "启动模式" -> LaunchModeActivity.startActivity(this)
            "IPC" -> IPCMainActivity.startActivity(this)
            "组件" -> ComponentsActivity.startAction(this)
            "ContentProvider" -> ProviderActivity.startActivity(this)
            "Event" -> EventActivity.startActivity(this)
            "Glide" -> GlideActivity.startActivity(this)
            "Hook" -> startActivity(Intent(this, HookActivity::class.java))
            "Permission" -> PermissionActivity.startActivity(this)
            "Web" -> WebActivity.startActivtiy(this)
            "通知" -> NotifyActivity.startActivity(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        setupView()
    }

    private fun setupView() {
//        viewManager = LinearLayoutManager(this)
        viewManager = GridLayoutManager(this, 4)
        viewAdapter = MyAdapter(items).apply { this.onItemClickListener = this@MainActivity }
        my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        Log.d(TAG, "onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}

class MyAdapter(private val data: Array<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    lateinit var onItemClickListener: OnItemClickListener

    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.my_text_view, parent, false) as TextView
        val holder = MyViewHolder(textView)
        textView.setOnClickListener { onItemClickListener?.onItemClick(textView, holder.adapterPosition) }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = data[position]
    }

    override fun getItemCount() = data.size
}

interface OnItemClickListener {
    fun onItemClick(v: View, position: Int)
}