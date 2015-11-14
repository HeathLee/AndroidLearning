package com.example.heath.hw_4;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private MyBroadcastReceiver myBroadcastReceiver;
    private boolean isRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isRegistered = false;
        final Button button = (Button) findViewById(R.id.button);
        final Button sendButton = (Button) findViewById(R.id.send);
        final EditText editText = (EditText) findViewById(R.id.msg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = button.getText().toString();
                if (!isRegistered) {
                    intentFilter = new IntentFilter();
                    intentFilter.addAction("com.example.heath.hw_4.MESSAGE");
                    myBroadcastReceiver = new MyBroadcastReceiver();
                    registerReceiver(myBroadcastReceiver, intentFilter);
                    isRegistered = true;
                    button.setText("Unregister");
                } else {
                    unregisterReceiver(myBroadcastReceiver);
                    isRegistered = false;
                    button.setText("Register");
                }
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.heath.hw_4.MESSAGE");
                intent.putExtra("message", editText.getText().toString());
                //Log.d("xyz", editText.getText().toString());
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegistered) {
            unregisterReceiver(myBroadcastReceiver);
        }
    }
}
