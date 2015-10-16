package com.example.heath.fragmentbestpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        News news = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;
        }
        TextView textView = (TextView) view.findViewById(R.id.news_title);
        textView.setText(news.getTitle());
        return view;
    }
}
