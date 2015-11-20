package com.example.heath.filepersistencetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heath.frilepersistencetest.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit);
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)) {
            editText.setText(inputText);
            editText.setSelection(inputText.length());
            Toast.makeText(this, "Reloadind succeeded", Toast.LENGTH_SHORT)
                    .show();;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = editText.getText().toString();
        save(inputText);
    }

    public void save(String inputText) {
        FileOutputStream outputStream = null;
        BufferedWriter bufferedWriter= null;
        try {
            outputStream = openFileOutput("data", MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter
                    (outputStream));
            bufferedWriter.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String load() {
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();

        try {
            inputStream = openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader
                    (inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
