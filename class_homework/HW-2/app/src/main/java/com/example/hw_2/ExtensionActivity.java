package com.example.hw_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by heath on 15-10-23.
 */
public class ExtensionActivity extends AppCompatActivity implements View
        .OnClickListener {
    private TextView textView;
    private Button[] btns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btns = new Button[9];
        String str = new String("123456789");
        setContentView(R.layout.extension_layout);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);
        textView = (TextView) findViewById(R.id.play_text);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridlayout);

        Button back = (Button) findViewById(R.id.back);

        final Intent intent = new Intent(this, MainActivity.class);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        for (int i = 0; i < str.length(); ++i) {
            Button btn = new Button(this);
            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup
                    .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                    .WRAP_CONTENT));
            GridLayout.Spec row = GridLayout.spec(i / 3);
            GridLayout.Spec col = GridLayout.spec(i % 3);
            btn.setId(i);
            btn.setText("" + str.charAt(i));
            btn.setOnClickListener(this);
            GridLayout.LayoutParams paras= new GridLayout.LayoutParams(row,
                    col);
            gridLayout.addView(btn);
            btns[i] = btn;
        }

    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < 9; ++i) {
            if (v.getId() == btns[i].getId()) {
                textView.setText(textView.getText().toString() + btns[i]
                        .getText().toString());
            }
        }
    }
}
