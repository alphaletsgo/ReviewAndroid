package cn.isif.aidlservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var btStart:Button
    lateinit var btStop:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btStart = findViewById(R.id.bt_start_service)
        btStop = findViewById(R.id.bt_stop_service)
        btStart.setOnClickListener {
            AIDLService.startService(this)
        }

        btStop.setOnClickListener {
            AIDLService.stopService(this)
        }
    }
}
