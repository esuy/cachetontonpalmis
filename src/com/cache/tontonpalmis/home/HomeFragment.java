package com.cache.tontonpalmis.home;

import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.profil.Profil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	View view;
    Profil profil;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = super.onCreateView(inflater, container, savedInstanceState);
		try {
			view = (RelativeLayout) inflater.inflate(R.layout.home_fragment,
					container, false);
			this.profil = (Profil)this.getActivity().getIntent().getSerializableExtra("profil");
			
			
		} catch (Exception ex) {
			Toast.makeText(this.getActivity().getApplicationContext(),
					ex.getMessage(), Toast.LENGTH_LONG).show();
			view = super.onCreateView(inflater, container, savedInstanceState);
		}
		return view;
	}
    
	
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		populate(view);
	}

	public void populate(View view) {
		TextView tvName = (TextView)view.findViewById(R.id.tvName);
		tvName.setText(profil.getName());
		TextView tvZone = (TextView)view.findViewById(R.id.tvZone);
		tvZone.setText(" "+profil.getZone()+" ");
		TextView tvNetwork = (TextView)view.findViewById(R.id.tvNetwork);
		tvNetwork.setText(profil.getOperator());
		TextView tvNumber = (TextView)view.findViewById(R.id.tvNumber);
		tvNumber.setText(profil.getTel().replace("+509", ""));
		//Log.i("Name", "..."+tvName.getText().toString());
	}

}
