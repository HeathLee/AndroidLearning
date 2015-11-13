package com.example.hw3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by heath on 15-10-30.
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int layoutId;

    public FruitAdapter(Context context, int layoutId,
                        List<Fruit> object) {
        super(context, layoutId, object);
        this.layoutId = layoutId;
    }

    class ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(layoutId, null);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) view.findViewById(com.example.hw3.R.id
                    .fruit_image);
            viewHolder.fruitName = (TextView) view.findViewById(com.example.hw3.R.id
                    .fruit_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.fruitImage.setImageResource(fruit.getFruitImageId());
        viewHolder.fruitName.setText(fruit.getFruitName());
        return view;
    }
}
