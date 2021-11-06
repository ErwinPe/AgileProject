package com.eseo.finaspetit.agileproject.main.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.main.library.Notification;
import java.util.List;

public class CustomClassAdaptater extends BaseAdapter {

    private final List<Notification> listData;
    private final LayoutInflater layoutInflater;
    private final Context context;

    public CustomClassAdaptater(Context aContext,  List<Notification> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_notif, null);
            holder = new ViewHolder();
            holder.logoView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.textViewtitle = (TextView) convertView.findViewById(R.id.textViewtitle);
            holder.textViewDesc = (TextView) convertView.findViewById(R.id.textViewDesc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Notification notif= this.listData.get(position);
        holder.textViewtitle.setText(notif.getTitle());
        holder.textViewDesc.setText(notif.getMessage());
        int imageId = this.getMipmapResIdByName("ic_launcher");
        holder.logoView.setImageResource(imageId);
        return convertView;
    }

    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        return context.getResources().getIdentifier(resName , "mipmap", pkgName);
    }

    static class ViewHolder {
        ImageView logoView;
        TextView textViewtitle;
        TextView textViewDesc;
    }
}
