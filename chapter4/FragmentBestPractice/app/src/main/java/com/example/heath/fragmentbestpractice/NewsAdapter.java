package com.example.heath.fragmentbestpractice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by heath on 15-10-13.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    private int resourceId;

    public NewsAdapter(Context context, int textViewRersourceId, List<News>
            objects) {
        super(context, textViewRersourceId, objects);
        resourceId = textViewRersourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    }
}
