package com.example.heath.hw_9;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public final static int UPDATE = 1, FAILED = 2;
    private static final String BASE =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private EditText mStrEditText;
    private Button mButtonButton;
    private ImageView mResultImageView;
    private TextView mValidateResult, mErrorInfo;
    static String str;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStrEditText = (EditText) findViewById(R.id.str);
        mButtonButton = (Button) findViewById(R.id.button);
        mResultImageView = (ImageView) findViewById(R.id.result);
        mValidateResult = (TextView) findViewById(R.id.validate_result);
        mErrorInfo = (TextView) findViewById(R.id.error_info);

        mButtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mErrorInfo.setText("");
//                str = getRandomStr(4);
//                dialog = new ProgressDialog(MainActivity.this);
//                dialog.setCancelable(false);
//                dialog.show();
//                new Thread(new Download(handler)).start();
                Log.d("xyz", str);
                Log.d("xyz", mStrEditText.getText().toString());
                if (str.equals(mStrEditText.getText().toString())) {
                    mValidateResult.setText("验证码输入正确");
                } else {
                    mValidateResult.setText("验证码输入错误，请重新输入");
                    getValidateCodeImage();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValidateCodeImage();
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
                    mErrorInfo.setText(msg.obj.toString());
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
            }
        }
    };

    private String getRandomStr(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            buffer.append(BASE.charAt(random.nextInt(62)));
        }
        return buffer.toString();
    }

    private void getValidateCodeImage() {
        str = getRandomStr(4);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.show();
        new Thread(new Download(handler)).start();
    }
}
