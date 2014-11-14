package com.cache.tontonpalmis.profil;

import java.util.List;

import com.cache.tontonpalmis.R;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class CustomListViewAdapter extends ArrayAdapter<RowItem> {
	Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<RowItem> items) {
    	super(context, resourceId, items);
        this.context = context;
    }
    public void setItemsList(List<RowItem> items){
    	this.clear();
    	for(RowItem item:items){
    		this.add(item);
    	}
    }

    /* private view holder class */
    private class ViewHolder {
    	TextView tvTelPhone;
    	LinearLayout layoutStar;
    	TextView tvZoneContact;
    	TextView tvTelIcon;
    	TextView tvNameProfil;
        ImageView userImage;
        TextView tvGain;
        TextView tvPerte;
        TextView tvprofil_icon;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_contact, null);
            holder = new ViewHolder();
            holder.tvTelPhone = (TextView)convertView.findViewById(R.id.tvTelPhone);
            holder.tvprofil_icon = (TextView)convertView.findViewById(R.id.tvprofil_icon);
            holder.layoutStar = (LinearLayout)convertView.findViewById(R.id.layoutStar);
            holder.tvZoneContact = (TextView)convertView.findViewById(R.id.tvZoneContact);
            holder.tvTelIcon = (TextView)convertView.findViewById(R.id.tvTelIcon);
            holder.tvNameProfil = (TextView)convertView.findViewById(R.id.tvNameProfil);
            holder.userImage = (ImageView)convertView.findViewById(R.id.userImage);
            holder.tvGain = (TextView)convertView.findViewById(R.id.tvGain);
            holder.tvPerte = (TextView)convertView.findViewById(R.id.tvPerte);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        
        holder.tvTelPhone.setText("  "+rowItem.getProfil().getTel().replace("+509", ""));
        
        if(!rowItem.getProfil().isFriends()){
			Drawable img = context.getResources().getDrawable(android.R.drawable.ic_input_add);
			img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
			holder.tvprofil_icon.setCompoundDrawables(img, null, null, null);
		}
        
        holder.tvZoneContact.setText(rowItem.getProfil().getZone());
        holder.tvNameProfil.setText(rowItem.getProfil().getName());
        return convertView;
    }
}