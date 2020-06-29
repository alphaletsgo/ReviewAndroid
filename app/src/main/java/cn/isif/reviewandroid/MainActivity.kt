package cn.isif.reviewandroid

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.isif.reviewandroid.components.ComponentsActivity
import cn.isif.reviewandroid.glide.GlideActivity
import cn.isif.reviewandroid.provider.ProviderActivity
import cn.isif.reviewandroid.ipc.IPCMainActivity
import cn.isif.reviewandroid.launchmode.LaunchModeActivity
import cn.isif.reviewandroid.services.ServiceActivity
import cn.isif.reviewandroid.views.EventActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private val items = arrayOf("启动模式","IPC","View","动画","数据库", "服务","组件","ContentProvider","Event","Glide")
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
    }

    private fun setupView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(items).apply { this.onItemClickListener = this@MainActivity }
        my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
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