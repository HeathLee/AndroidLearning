package com.example.hw_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final String NAME = "LeiBusi";
    private final String PASSWORD = "Halo3Q";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton imageButton = (ImageButton) findViewById(R.id
                .imagebutton);
        final Button button = (Button) findViewById(R.id.button);
        final EditText userNanme = (EditText) findViewById(R.id.user_name);
        final EditText password = (EditText) findViewById(R.id.password);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hw2);
        final Button ext = (Button) findViewById(R.id.ext);

        final Intent intent = new Intent(this, ExtensionActivity.class);
        ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userNanme.getText().toString().equals(NAME)) {
                    userNanme.setError("用户名错误");
                    userNanme.setText("");
                    userNanme.requestFocus();
                } else if (!password.getText().toString().equals(PASSWORD)) {
                    password.setError("密码错误");
                    password.setText("");
                    password.requestFocus();
                } else {
                    imageButton.setImageResource(R.drawable.state2);
                    userNanme.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNanme.setText("");
                userNanme.setVisibility(View.VISIBLE);
                password.setText("");
                password.setVisibility(View.VISIBLE);
                imageButton.setImageResource(R.drawable.state1);
                TextView textView = new TextView(MainActivity.this);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup
                        .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                        .WRAP_CONTENT));
                textView.setText("这是动态添加的textView");
                linearLayout.addView(textView);
            }
        });

        imageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView textView = new TextView(MainActivity.this);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup
                        .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                        .WRAP_CONTENT));
                textView.setText("这是动态添加的textView");
                linearLayout.addView(textView);
                return  true;
            }
        });
    }
}
