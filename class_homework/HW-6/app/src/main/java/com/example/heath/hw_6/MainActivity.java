package com.example.heath.hw_6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList fileList;
    private AutoCompleteTextView autoCompleteTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileList = new ArrayList();
        for (File f : this.getFilesDir().listFiles()) {
            fileList.add(f.getName());
        }
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .file_name);
        autoCompleteTextView.setAdapter(new ArrayAdapter<>(this, android.R
                .layout.simple_dropdown_item_1line, fileList));

        final FileUtils fileUtil = new FileUtils();
        Button save = (Button) findViewById(R.id.save);
        Button read = (Button) findViewById(R.id.read);
        Button delete = (Button) findViewById(R.id.delete);
        final EditText fileName = (EditText) findViewById(R.id.file_name);
        final EditText fileContent = (EditText) findViewById(R.id.file_content);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().toString() != "") {
                    fileUtil.saveContent(MainActivity.this, fileName.getText
                            ().toString(), fileContent.getText().toString());
                    fileList.add(fileName.getText().toString());
                    autoCompleteTextView.setAdapter(new ArrayAdapter<String>
                            (MainActivity.this, android.R
                            .layout.simple_dropdown_item_1line, fileList));
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().toString() != "") {
                    String content = fileUtil.getContext(MainActivity.this,
                            fileName.getText().toString());
                    fileContent.setText(content);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().toString() != "") {
                    fileUtil.delteFile(MainActivity.this, fileName.getText().toString());
                    fileList.remove(fileName.getText().toString());
                    autoCompleteTextView.setAdapter(new ArrayAdapter<String>
                            (MainActivity.this, android.R
                                    .layout.simple_dropdown_item_1line, fileList));
                    fileContent.setText("");
                    fileName.setText("");
                }
            }
        });
    }
}
