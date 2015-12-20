package com.example.heath.hw_9;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final static int UPDATE = 1, FAILED = 2;

    private EditText mStrEditText;
    private Button mButtonButton;
    private ImageView mResultImageView;
    static String str;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStrEditText = (EditText) findViewById(R.id.str);
        mButtonButton = (Button) findViewById(R.id.button);
        mResultImageView = (ImageView) findViewById(R.id.result);

        mButtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = mStrEditText.getText().toString();
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setCancelable(false);
                dialog.show();
                new Thread(new Download(handler)).start();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    byte[] data = Base64.decode(msg.obj.toString().getBytes()
                            , Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                            data.length);
                    mResultImageView.setImageBitmap(bitmap);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
                case FAILED:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
            }
        }
    };
}
