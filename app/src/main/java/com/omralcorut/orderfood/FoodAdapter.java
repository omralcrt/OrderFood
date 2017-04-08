package com.omralcorut.orderfood;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by omral on 24.12.2016.
 */

//Food adapter for custom display food items.
public class FoodAdapter extends ArrayAdapter<Food> {

    private int img = 0;

    public FoodAdapter(Context context, List<Food> objects, int img) {
        super(context, 0, objects);
        this.img = img;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_food, parent, false
            );
        }
        Food currentFood = getItem(position);

        // Which image will be showing
        ImageView image = (ImageView) listItemView.findViewById(R.id.img);
        if (img == 0) {
            image.setImageResource(R.drawable.plus);
        }
        else if (img == 1) {
            image.setImageResource(R.drawable.cancel);
        }
        else {
            image.setImageResource(R.drawable.normal);
        }

        TextView name = (TextView) listItemView.findViewById(R.id.name);
        name.setText(currentFood.getName());

        TextView cost = (TextView) listItemView.findViewById(R.id.cost);
        cost.setText(currentFood.getCost());

        return listItemView;
    }
}
