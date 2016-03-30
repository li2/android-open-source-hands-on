package com.paging.listview.sample;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MyPagingAdaper extends ArrayAdapter<String> {

    protected List<String> items;

    public MyPagingAdaper(Context context) {
        super(context, 0);
        this.items = new ArrayList<>();
    }

    public void addMoreItems(List<String> newItems) {
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void addMoreItems(int location, List<String> newItems) {
        this.items.addAll(location, newItems);
        notifyDataSetChanged();
    }

    public void removeAllItems() {
        this.items.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        String text = getItem(position);

        if (convertView != null) {
            textView = (TextView) convertView;
        } else {
            textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_1, null);
        }
        textView.setText(text);
        return textView;
    }
}
