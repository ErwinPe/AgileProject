package com.eseo.finaspetit.agileproject.main.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.main.library.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomClassAdaptaterMessage extends BaseAdapter {

    private List<Message> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomClassAdaptaterMessage(Context aContext,  List<Message> listData) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_message, null);
            holder = new ViewHolder();
            holder.txtUser = (TextView) convertView.findViewById(R.id.textViewUser);
            holder.txtMsg = (TextView) convertView.findViewById(R.id.textViewMessageTxt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Message msg= this.listData.get(position);
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy à HH:mm").format(msg.getMessageTime().toDate());
        holder.txtUser.setText(msg.getMessageUser()+"  "+formattedDate);
        holder.txtMsg.setText(msg.getMessageText());
        return convertView;
    }

    static class ViewHolder {
        TextView txtUser;
        TextView txtMsg;
    }
}
