package com.omralcorut.orderfood;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by omral on 26.12.2016.
 */

public class FoodHistoryAdapter extends ArrayAdapter<History> {

    public FoodHistoryAdapter(Context context, List<History> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_history, parent, false
            );
        }
        History currentHistory = getItem(position);

        TextView table = (TextView) listItemView.findViewById(R.id.table_number);
        table.setText(currentHistory.getTableNumber());

        TextView name = (TextView) listItemView.findViewById(R.id.event);
        name.setText(currentHistory.getEvent());

        Date d = new Date(Long.parseLong(currentHistory.getDate()));

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(d);
        dateView.setText(formattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(d);
        timeView.setText(formattedTime);

        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
