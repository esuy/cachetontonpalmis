package com.cache.tontonpalmis.home;

import com.cache.tontonpalmis.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MessageFragment extends Fragment {
	View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		view = super.onCreateView(inflater, container, savedInstanceState);
		view = (RelativeLayout) inflater.inflate(R.layout.message_fragment, container, false);
		return view;
	}
	

}
