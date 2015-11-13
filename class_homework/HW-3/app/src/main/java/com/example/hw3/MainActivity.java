package com.example.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fruit> fruit;

    private void init() {
        fruit = new ArrayList<>();
        fruit.add(new Fruit("apple", com.example.hw3.R.drawable.apple));
        fruit.add(new Fruit("banana", com.example.hw3.R.drawable.banana));
        fruit.add(new Fruit("cherry", com.example.hw3.R.drawable.cherry));
        fruit.add(new Fruit("coco", com.example.hw3.R.drawable.coco));
        fruit.add(new Fruit("kiwi", com.example.hw3.R.drawable.kiwi));
        fruit.add(new Fruit("orange", com.example.hw3.R.drawable.orange));
        fruit.add(new Fruit("pear", com.example.hw3.R.drawable.pear));
        fruit.add(new Fruit("strawberry", com.example.hw3.R.drawable.strawberry));
        fruit.add(new Fruit("watermelon", com.example.hw3.R.drawable.watermelon));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.hw3.R.layout.activity_main);
        init();
        final ListView listView = (ListView) findViewById(com.example.hw3.R.id.list_view);
        final FruitAdapter fruitAdapter = new FruitAdapter(MainActivity.this, com.example.hw3.R
                .layout.fruit_item, fruit);
        listView.setAdapter(fruitAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SecondActivity
                        .class);
                Bundle bundle = new Bundle();
                bundle.putString("fruitName", fruit.get(position).getFruitName
                        ());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                fruitAdapter.remove(fruitAdapter.getItem(position));
                return true;
            }
        });
    }
}
