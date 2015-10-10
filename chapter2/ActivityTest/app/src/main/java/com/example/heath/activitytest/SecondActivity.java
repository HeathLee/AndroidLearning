package com.example.heath.activitytest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by heath on 15-9-12.
 */
public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("xyz SecondActivity", "Task ID is " + getTaskId());
        setContentView(R.layout.second_layout);
        final Intent intent = getIntent();
        final String extra_data = intent.getStringExtra("extra_data");
        Button button = (Button) findViewById(R.id.button_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SecondActivity.this, extra_data, Toast
//                        .LENGTH_SHORT).show();
                Intent intent1 = new Intent(SecondActivity.this,
                        ThirdActivity.class);
//                intent1.putExtra("result_data", "hello");
//                setResult(RESULT_OK, intent1);
//                finish();
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SecondActivity", "onDestory");
    }
}
