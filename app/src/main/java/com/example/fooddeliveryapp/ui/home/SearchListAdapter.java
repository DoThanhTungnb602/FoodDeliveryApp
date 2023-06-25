package com.example.fooddeliveryapp.ui.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.List;

public class SearchListAdapter extends BaseAdapter {
    private List<String> nameFoods;

    public SearchListAdapter(List<String> nameFoods) {
        this.nameFoods = nameFoods;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return this.nameFoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder
        return null;
    }
    public static class ViewHolder{
        EditText eidtText;
    }
}
