package cn.isif.rxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Context mContext;
    final String TAG = "MainActivity";
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        bt = findViewById(R.id.bt_get_repo);
        bt.setOnClickListener(v -> {
            GetRepoActivity.Companion.startActivity(this);
        } );
    }

}
