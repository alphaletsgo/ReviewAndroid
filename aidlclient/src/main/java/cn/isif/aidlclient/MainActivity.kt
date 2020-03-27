package cn.isif.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import cn.isif.aidlservice.IManager

class MainActivity : AppCompatActivity() {
    private lateinit var btBind: Button
    private lateinit var btUnbind: Button
    private lateinit var btAdd: Button
    private lateinit var btMin: Button

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
        btAdd = findViewById(R.id.bt_add)
        btMin = findViewById(R.id.bt_min)

        btBind.setOnClickListener {
            //绑定服务:aidl 只能使用action的方式绑定服务
            val intent = createExplicitFromImplicitIntent(this,Intent("cn.isif.aidlservice.AIDLSERVICE"))
            bindService(intent, sc, Context.BIND_AUTO_CREATE)
        }
        btUnbind.setOnClickListener {
            //解绑服务
            unbindService(sc)
        }
        btAdd.setOnClickListener {
            Log.d(TAG, "The ADD invoked result is: ${im.add(3, 4)}")
        }

        btMin.setOnClickListener {
            Log.d(TAG, "The MIN invoked result is: ${im.min(3, 4)}")
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
