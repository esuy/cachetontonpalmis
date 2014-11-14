package com.cache.tontonpalmis.chat;

import java.util.ArrayList;
import java.util.List;

import com.cache.tontonpalmis.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DiscussArrayAdapter extends ArrayAdapter<Message> {

	private TextView countryName;
	private LinearLayout pan;
	private List<Message> countries = new ArrayList<Message>();
	private LinearLayout wrapper;
	private TextView date;
	@Override
	public void add(Message object) {
		countries.add(object);
		super.add(object);
	}

	public DiscussArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return this.countries.size();
	}

	public Message getItem(int index) {
		return this.countries.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_discuss, parent, false);
		}

		wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

		MessageText message = (MessageText)getItem(position);
		
		countryName = (TextView) row.findViewById(R.id.comment);
		date = (TextView) row.findViewById(R.id.date);
		pan = (LinearLayout) row.findViewById(R.id.lcomment);
		countryName.setText(message.getMessage());
		date.setText("");
		if(position==0){
			date.setText("October 23, 2014");
		}
		

		pan.setBackgroundResource(message.isLeft() ? R.drawable.bubble_yellow : R.drawable.bubble_green);
		wrapper.setGravity(message.isLeft() ? Gravity.LEFT : Gravity.RIGHT);

		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}