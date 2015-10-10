package com.example.heath.uibestpractice;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<>();

    private void initMsgs() {
        Msg msg = new Msg("Hello guy", Msg.TYPE_RECEIVED);
        msgList.add(msg);
        Msg msg1 = new Msg("Hello. Who is that?", Msg.TYPE_SEND);
        msgList.add(msg1);
        Msg msg2 = new Msg("This is Tom", Msg.TYPE_RECEIVED);
        msgList.add(msg2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsgs();
        adapter = new MsgAdapter(MainActivity.this, R.layout
                .msg_item, msgList);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String conntent = inputText.getText().toString();
                if (!"".equals(conntent)) {
                    Msg msg = new Msg(conntent, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
