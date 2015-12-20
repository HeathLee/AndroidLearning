package com.example.heath.hw_8;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private static final String QUERY_URL = "http://webservice.webxml.com" +
            ".cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo";
//    private static final String QUERY_URL = "https://tcc.taobao" +
//        ".com/cc/json/mobile_tel_segment.htm?tel=";
    private static final int UPDATE_CONTENT = 1;
    private EditText mPhoneEditText;
    private Button mSeachButton;
    private TextView mContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPhoneEditText = (EditText) findViewById(R.id.phone);
        mSeachButton = (Button) findViewById(R.id.seach);
        mContentTextView = (TextView) findViewById(R.id.content);

        mSeachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentTextView.setText("");
                sendRequestWithHttpURLConnection(mPhoneEditText.getText()
                        .toString());
            }
        });
    }

    private void sendRequestWithHttpURLConnection(final String phone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) ((new URL(QUERY_URL))
                            .openConnection());
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(40000);
                    connection.setConnectTimeout(40000);

                    DataOutputStream outputStream = new DataOutputStream
                            (connection.getOutputStream());
                    outputStream.writeBytes("mobileCode=" + phone + "&userID=");

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(inputStream, "utf8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String str = response.toString();
                    int point = str.indexOf("<string");
                    str = str.substring(point);
                    str = "<xml>" + str + "</xml>";
                    inputStream = new ByteArrayInputStream(str.getBytes());
//                    JSONObject jsonObject = new JSONObject(str);
                    Message message = new Message();
                    message.what = UPDATE_CONTENT;
                    message.obj = parseXMLWithDOM(inputStream);
//                    message.obj = parseXMLWithPull(response.toString());
//                    message.obj = jsonObject.getString("province") + " " +
//                            jsonObject.getString("catName");
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = new Message();
                    message.what = UPDATE_CONTENT;
                    message.obj = e.toString();
                    handler.sendMessage(message);
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_CONTENT:
                    mContentTextView.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };

    private String  parseXMLWithPull(String xml) {
        String str = "";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("string".equals(parser.getName())) {
                            str = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String parseXMLWithDOM(InputStream xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            Element element = document.getDocumentElement();
            NodeList string = element.getElementsByTagName("string");
            return string.item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
