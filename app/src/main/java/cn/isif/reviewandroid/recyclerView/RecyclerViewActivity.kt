package cn.isif.reviewandroid.recyclerView

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.isif.reviewandroid.R

/**
 * recyclerview 学习
 */
class RecyclerViewActivity : AppCompatActivity() {
    private val itemArray = arrayOf(
        "Item1",
        "Item2",
        "Item3",
        "Item4",
        "Item5",
        "Item6",
        "Item7",
        "Item8",
        "Item9",
        "Item10",
        "Item11",
        "Item12",
        "Item13",
        "Item14",
        "Item15",
        "Item16"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        findViewById<RecyclerView>(R.id.list)?.apply {
            layoutManager =
                LinearLayoutManager(this@RecyclerViewActivity, RecyclerView.HORIZONTAL, false)
            adapter = DemoAdapter(this@RecyclerViewActivity, itemArray)
            this.post {
                adapter?.let {
                    if (it.itemCount > 3) {
                        var realWidth = 0
                        for (i in 0..3) {
                            val item = this.getChildAt(i)
                            val measureWidth = item.width
                            realWidth += measureWidth
                        }
                        val layoutParams = layoutParams
                        layoutParams.width = realWidth
                        setLayoutParams(layoutParams)
                    }
                }
            }
        }
    }

    class DemoAdapter(val context: Context, private val values: Array<String>) :
        RecyclerView.Adapter<DemoAdapter.DemoViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
            Log.d(TAG, "DemoAdapter - onCreateViewHolder")
            val itemView = View.inflate(context, R.layout.layout_item_text, null)
            return DemoViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return values.size
        }

        override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
            Log.d(TAG, "DemoAdapter - onBindViewHolder: $position")
            holder.itemText?.let {
                it.text = values[position]
            }
        }

        class DemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val itemText: TextView? = view.findViewById(R.id.text)
        }
    }

    companion object {
        const val TAG = "RecyclerViewActivity"
        fun startActivity(context: Context?) {
            context?.apply {
                startActivity(Intent(this, RecyclerViewActivity::class.java))
            }
        }
    }
}