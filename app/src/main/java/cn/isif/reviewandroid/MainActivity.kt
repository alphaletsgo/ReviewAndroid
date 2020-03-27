package cn.isif.reviewandroid

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.isif.reviewandroid.services.ServiceActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private val items = arrayOf("启动模式","IPC","View","动画","数据库", "服务")
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //recyclerView列表点击处理
    override fun onItemClick(v: View, position: Int) {
        when (items[position]) {
            "服务" -> ServiceActivity.actionStart(this)
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