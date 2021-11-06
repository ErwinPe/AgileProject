package com.eseo.finaspetit.agileproject.main.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.main.library.US;
import java.util.ArrayList;

public class CustomClassAdaptaterUS extends BaseAdapter {
    private final ArrayList<US> listData;
    private final LayoutInflater layoutInflater;

    public CustomClassAdaptaterUS(Context aContext, ArrayList<US> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CustomClassAdaptaterUS.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_us, null);
            holder = new CustomClassAdaptaterUS.ViewHolder();
            holder.txtUser = (TextView) convertView.findViewById(R.id.textViewtitle);
            holder.txtMsg = (TextView) convertView.findViewById(R.id.textViewDesc);
            convertView.setTag(holder);
        } else {
            holder = (CustomClassAdaptaterUS.ViewHolder) convertView.getTag();
        }
        US us= this.listData.get(position);
        holder.txtUser.setText(us.getNom());
        holder.txtMsg.setText(us.getDescription());
        return convertView;
    }

    static class ViewHolder {
        TextView txtUser;
        TextView txtMsg;
    }
}
