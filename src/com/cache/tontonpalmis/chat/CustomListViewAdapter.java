package com.cache.tontonpalmis.chat;

import java.util.List;

import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.profil.Profil;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;



public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;
    Profil profil;
    public CustomListViewAdapter(Profil profil,Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
        this.profil = profil;
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView imageView;
        ListView lstMsg;
        TextView tvGain;
        TextView tvPerte;
        TextView tvName;
        TextView tvZone;
        TextView tvDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);
        Profil profil = rowItem.getProfil();
        List<Message> messages = rowItem.getMessages();
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_chat, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.tvGain = (TextView)convertView.findViewById(R.id.tvGain);
        holder.tvPerte = (TextView)convertView.findViewById(R.id.tvPerte);
        holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
        holder.tvName.setText(profil.getName());
        holder.tvZone = (TextView)convertView.findViewById(R.id.tvZone);
        holder.tvZone.setText(" "+profil.getZone()+" ");
        holder.tvDate = (TextView)convertView.findViewById(R.id.tvDate);
        
        return convertView;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public RowItem getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public int getPosition(RowItem item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
	}
    
    
    
}