package com.example.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by heath on 15-10-30.
 */
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.hw3.R.layout.second_activity_layout);
        Bundle bundle = getIntent().getExtras();
        TextView textView = (TextView) findViewById(com.example.hw3.R.id.text);
        textView.setText("I love " + bundle.get("fruitName") + "!!!");
        Button button = (Button) findViewById(com.example.hw3.R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity
                        .class);
                startActivity(intent);
            }
        });
    }
}
