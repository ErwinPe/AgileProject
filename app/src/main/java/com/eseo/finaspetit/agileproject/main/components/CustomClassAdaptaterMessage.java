package com.eseo.finaspetit.agileproject.main.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.main.library.Message;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CustomClassAdaptaterMessage extends BaseAdapter {

    private final List<Message> listData;
    private final LayoutInflater layoutInflater;

    public CustomClassAdaptaterMessage(Context aContext,  List<Message> listData) {
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
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy à HH:mm", Locale.FRANCE).format(msg.getMessageTime().toDate());
        String headerText=msg.getMessageUser()+"  "+formattedDate;
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) holder.txtMsg.getLayoutParams();
        params.height = (msg.getMessageText().length()/24+1)*150;
        holder.txtMsg.setLayoutParams(params);
        holder.txtUser.setText(headerText);
        holder.txtMsg.setText(msg.getMessageText());
        return convertView;
    }

    static class ViewHolder {
        TextView txtUser;
        TextView txtMsg;
    }

}
