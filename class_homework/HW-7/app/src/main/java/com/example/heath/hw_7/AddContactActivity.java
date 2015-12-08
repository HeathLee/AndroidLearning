package com.example.heath.hw_7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by heath on 15-12-7.
 */
public class AddContactActivity extends AppCompatActivity {

    private EditText mSidEditText;
    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private Button mConfirmButton;
    private MyDatabaseHelper myDatabaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        mSidEditText = (EditText) findViewById(R.id.sid);
        mNameEditText = (EditText) findViewById(R.id.name);
        mPhoneEditText = (EditText) findViewById(R.id.phone);
        mConfirmButton = (Button) findViewById(R.id.confirm);
        myDatabaseHelper = new MyDatabaseHelper(this);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid = mSidEditText.getText().toString();
                String name = mNameEditText.getText().toString();
                String phone = mPhoneEditText.getText().toString();

                if (TextUtils.isEmpty(sid) || TextUtils.isEmpty(name) ||
                        TextUtils.isEmpty(phone)) {
                    Toast.makeText(AddContactActivity.this, "请填写完整信息", Toast
                            .LENGTH_SHORT).show();
                } else {
                    Contact contact = new Contact();
                    contact.setSid(sid);
                    contact.setName(name);
                    contact.setPhone(phone);
                    myDatabaseHelper.insert(contact);
                    Toast.makeText(AddContactActivity.this, "添加成功", Toast
                            .LENGTH_SHORT).show();
                    Intent intent = new Intent(AddContactActivity.this, MainActivity
                            .class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
