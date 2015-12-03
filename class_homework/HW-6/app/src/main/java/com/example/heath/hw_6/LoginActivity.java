package com.example.heath.hw_6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by heath on 15-11-15.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText accountEdit, passwordEdit;
    private Button login, register;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private AESUtil aesUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        rememberPass = (CheckBox) findViewById(R.id.rember_pass);

        //退出后重新进入登陆界面
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String account = pref.getString("account", null);
            String password = pref.getString("password", null);
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        aesUtil = new AESUtil();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                try {
                    password = aesUtil.encrypt(account, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                editor.clear();
                editor.putString("account", account);
                editor.putString("password", password);
                editor.commit();
                Toast.makeText(LoginActivity.this, "Register Success", Toast
                        .LENGTH_SHORT).show();
                accountEdit.setText("");
                passwordEdit.setText("");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String rightPass = null;
                try {
                    rightPass = aesUtil.decrypt(account, pref.getString
                            ("password", null));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //验证账号密码非空且正确
                if (!TextUtils.isEmpty(account) &&
                        account.equals(pref.getString("account", null)) &&
                        !TextUtils.isEmpty(password) &&
                        password.equals(rightPass)) {
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.commit();
                    } else {
                        accountEdit.setText("");
                        passwordEdit.setText("");
                    }
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "account or password " +
                            "is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rememberPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    editor.putBoolean("remember_password", false);
                    editor.commit();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rememberPass.isChecked()) {
            String account = pref.getString("account", null);
            accountEdit.setText(account);
            try {
                passwordEdit.setText(aesUtil.decrypt(account, pref.getString
                        ("password", null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            accountEdit.setText("");
            passwordEdit.setText("");
        }
    }
}
