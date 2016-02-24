package com.example.heath.contactstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView contactsView;
    ArrayAdapter<String> arrayAdapter;
    List<String> contcatsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactsView = (ListView) findViewById(R.id.contacts_view);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_expandable_list_item_1, contcatsList);
        contactsView.setAdapter(arrayAdapter);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission
                    .READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest
                                .permission.READ_CONTACTS)) {
                    //do nothing
                    ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.READ_CONTACTS}, 1);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.READ_CONTACTS}, 1);
                }
            } else  {
                readContacts();
            }
        } else {
            readContacts();
        }

    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract
                    .CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            Log.d("xyz", "readContacts: " + cursor.getCount());
            while (cursor.moveToNext()) {
                String displayName = cursor.getString(cursor.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                String number = cursor.getString(cursor.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.NUMBER));

                contcatsList.add(displayName + "\n" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager
                    .PERMISSION_GRANTED) {
                readContacts();
            }
        }
    }
}
