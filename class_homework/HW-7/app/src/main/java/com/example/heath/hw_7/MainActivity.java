package com.example.heath.hw_7;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        DeleteContactDialogFragment.DeleteDialogListener,
        UpdateContactDialogFragment.UpdateDialogListener{

    private static final int ADD_CONTACT = 1;

    private ListView mContactsListView;
    private List<Map<String, String>> mContactsList;
    private MyDatabaseHelper myDatabaseHelper;
    private Button mAddButton;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddButton = (Button) findViewById(R.id.add);
        mContactsListView = (ListView) findViewById(R.id.contacts);
        myDatabaseHelper = new MyDatabaseHelper(this);
        mContactsList = new ArrayList<>();
        adapter = new SimpleAdapter(this, mContactsList, R.layout
                .contact_item, new String[]{"sid", "name", "phone"},
                new int[]{R.id.sid, R.id.name, R.id.phone});


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity
                        .class);
                startActivityForResult(intent, ADD_CONTACT);
            }
        });

        mContactsListView.setOnItemClickListener(new AdapterView.
                OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Map<String, String> contact = mContactsList.get(position);
                Bundle args = new Bundle();
                args.putString("sid", contact.get("sid"));
                args.putString("name", contact.get("name"));
                args.putString("phone", contact.get("phone"));
                args.putInt("position", position);
                UpdateContactDialogFragment updateContactDialogFragment = new
                        UpdateContactDialogFragment();
                updateContactDialogFragment.setArguments(args);
                updateContactDialogFragment.show(getFragmentManager(),
                        "update");
            }
        });

        mContactsListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Delete")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                mContactsList.remove(position);
//                                myDatabaseHelper.delete()
//                                adapter.notifyDataSetChanged();
//                            }
//                        }).show();
                Map<String, String> contact = mContactsList.get(position);
                Bundle args = new Bundle();
                args.putSerializable("contact", new Contact(contact.get
                        ("sid"), contact.get("name"), contact.get("phone")));
                DeleteContactDialogFragment deleteContactDialogFragment = new
                        DeleteContactDialogFragment();
                deleteContactDialogFragment.setArguments(args);
                deleteContactDialogFragment.show(getFragmentManager(),
                        "delete");
                mContactsList.remove(contact);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData(mContactsList);
        mContactsListView.setAdapter(adapter);

    }

    private void setData(List<Map<String, String>> mContactsList) {
        mContactsList.clear();
        Map<String, String> mData;
        Cursor cursor = myDatabaseHelper.query();
        while (cursor.moveToNext()) {
            mData = new HashMap<String, String>();
            mData.put("sid", cursor.getString(cursor.getColumnIndex("sid")));
            mData.put("name", cursor.getString(cursor.getColumnIndex("name")));
            mData.put("phone", cursor.getString(cursor.getColumnIndex
                    ("phone")));
            mContactsList.add(mData);
        }
    }


    @Override
    public void onConfirmDelete(Contact contact) {
        myDatabaseHelper.delete(contact);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfirmUpdate(Contact contact, int position) {
        myDatabaseHelper.update(contact);
        Map<String, String> map = mContactsList.get(position);
        map.put("sid", contact.getSid());
        map.put("name", contact.getName());
        map.put("phone", contact.getPhone());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        switch (requestCode) {
            case ADD_CONTACT:
                if (resultCode == RESULT_OK) {
                    HashMap<String, String> mData = new HashMap<>();
                    String sid = data.getStringExtra("sid");
                    String name = data.getStringExtra("name");
                    String phone = data.getStringExtra("phone");

                    mData.put("sid", sid);
                    mData.put("name", name);
                    mData.put("phone", phone);
                    mContactsList.add(mData);
                    adapter.notifyDataSetChanged();
                    myDatabaseHelper.insert(new Contact(sid, name, phone));
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
