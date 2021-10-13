package com.eseo.finaspetit.agileproject.main.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.US;

import java.text.SimpleDateFormat;
import java.util.List;

public class CustomClassAdaptaterUS extends BaseAdapter {
    private List<US> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomClassAdaptaterUS(Context aContext, List<US> listData) {
        this.context = aContext;
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
        CustomClassAdaptaterMessage.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_us, null);
            holder = new CustomClassAdaptaterMessage.ViewHolder();
            holder.txtUser = (TextView) convertView.findViewById(R.id.textViewtitle);
            holder.txtMsg = (TextView) convertView.findViewById(R.id.textViewDesc);
            convertView.setTag(holder);
        } else {
            holder = (CustomClassAdaptaterMessage.ViewHolder) convertView.getTag();
        }

        US us= this.listData.get(position);
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy à HH:mm").format(us.getDateCreation().toDate());
        holder.txtUser.setText(us.getNom()+" - ["+formattedDate+"]");
        holder.txtMsg.setText(us.getDescription());
        return convertView;
    }

    static class ViewHolder {
        TextView txtUser;
        TextView txtMsg;
    }
}
