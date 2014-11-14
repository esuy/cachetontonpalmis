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
import android.widget.TextView;



public class CustomSmallListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;
    Profil profil;
    public CustomSmallListViewAdapter(Profil profil,Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
        this.profil = profil;
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
        TextView txtDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_chat, null);
            holder = new ViewHolder();
            
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        
        
        
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