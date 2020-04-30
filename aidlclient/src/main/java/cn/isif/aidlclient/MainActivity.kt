package cn.isif.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.Toast
import cn.isif.aidlservice.Book
import cn.isif.aidlservice.IManager

/**
 * AIDL测试客户端
 */
class MainActivity : AppCompatActivity() {
    private lateinit var btBind: Button
    private lateinit var btUnbind: Button
    private lateinit var btAddIn: Button
    private lateinit var btAddOut: Button
    private lateinit var btAddInout: Button
    private lateinit var btGetList: Button

    private lateinit var im: IManager

    private val sc: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected")
            im = IManager.Stub.asInterface(service)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btBind = findViewById(R.id.bt_bind_service)
        btUnbind = findViewById(R.id.bt_unbind_service)
        btAddIn = findViewById(R.id.bt_add_in)
        btAddOut = findViewById(R.id.bt_add_out)
        btAddInout = findViewById(R.id.bt_add_inout)
        btGetList = findViewById(R.id.bt_get_list)
        //绑定服务
        btBind.setOnClickListener {
            //绑定服务:aidl 只能使用action的方式绑定服务
            val intent = createExplicitFromImplicitIntent(this,Intent("cn.isif.aidlservice.AIDLSERVICE"))
            bindService(intent, sc, Context.BIND_AUTO_CREATE)
        }
        //解绑服务
        btUnbind.setOnClickListener {
            unbindService(sc)
        }
        //in：服务端接受book的内容，但不会将改变的结果回写给book
        btAddIn.setOnClickListener {
            var book = Book(100.0,"Android in")
            Log.d(TAG,"before:${book.toString()}")
            var result = im.addBookIn(book)
            if (result!=null){
                Log.d(TAG,"return:${result.toString()}")
            }
            Log.d(TAG,"after:${book.toString()}")
        }
        //out：服务端不会接受book的内容（会创建一个新的book，并对其初始化），但会将改变得结果回写给book
        btAddOut.setOnClickListener {
            var book = Book(100.0,"Android out")
            Log.d(TAG,"before:${book.toString()}")
            var result = im.addBookOut(book)
            if (result!=null){
                Log.d(TAG,"return:${result.toString()}")
            }
            Log.d(TAG,"after:${book.toString()}")
        }
        //inout：综合in和out方案
        btAddInout.setOnClickListener {
            var book = Book(100.0,"Android inout")
            Log.d(TAG,"before:${book.toString()}")
            var result = im.addBookInOut(book)
            if (result!=null){
                Log.d(TAG,"return:${result.toString()}")
            }
            Log.d(TAG,"after:${book.toString()}")
        }

        btGetList.setOnClickListener {
            var books = im.bookList
            Log.d(TAG,books.joinToString())
        }

    }

    companion object {
        private const val TAG = "MainActivity"
        /***
         * Android L (lollipop, API 21) introduced a new problem when trying to invoke implicit intent,
         * "java.lang.IllegalArgumentException: Service Intent must be explicit"
         *
         * If you are using an implicit intent, and know only 1 target would answer this intent,
         * This method will help you turn the implicit intent into the explicit form.
         *
         * Inspired from SO answer: http://stackoverflow.com/a/26318757/1446466
         * @param context
         * @param implicitIntent - The original implicit intent
         * @return Explicit Intent created from the implicit original intent
         */
        fun createExplicitFromImplicitIntent(context:Context, implicitIntent:Intent):Intent? {
            // Retrieve all services that can match the given intent
            val pm = context.packageManager
            val resolveInfo = pm.queryIntentServices(implicitIntent, 0)
            // Make sure only one match was found
            if (resolveInfo == null || resolveInfo.size != 1) {
                return null
            }
            // Get component info and create ComponentName
            val serviceInfo = resolveInfo.get(0)
            val packageName = serviceInfo.serviceInfo.packageName
            val className = serviceInfo.serviceInfo.name
            val component = ComponentName(packageName, className)
            // Create a new intent. Use the old one for extras and such reuse
            val explicitIntent = Intent(implicitIntent)
            // Set the component to be explicit
            explicitIntent.component = component
            return explicitIntent
        }
    }
}
