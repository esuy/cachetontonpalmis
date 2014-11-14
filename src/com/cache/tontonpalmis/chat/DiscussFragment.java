package com.cache.tontonpalmis.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.R.drawable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DiscussFragment extends Fragment {
	View view;
	ListView listView;
	List<RowItem> rowItems;
	List<Message> messages;
	private EditText editText1;
	private DiscussArrayAdapter adapter;
	private ListView lv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		new LoadViewTask().execute();
		

		// listView.setOnItemClickListener(this.getActivity().getApplication());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// view = super.onCreateView(inflater, container, savedInstanceState);
		view = (RelativeLayout) inflater.inflate(R.layout.discuss_panel,
				container, false);
		try{
			
			
			
			
	    }catch(Exception ex){
	    	NullPointerException n = (NullPointerException) ex;
	        StackTraceElement stackTrace = n.getStackTrace()[0];
			Toast.makeText(getActivity().getApplicationContext(), ex.toString()+" "+"Unexpected Exception due at " + stackTrace.getLineNumber(), 1000000).show();
		}
	
		

		return view;
	}

	private class LoadViewTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// super.onPreExecute();
			// setListAdapter(null);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			synchronized (this) {
				

			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			lv = (ListView) getActivity().findViewById(R.id.listView1);
			
			adapter = new DiscussArrayAdapter(getActivity().getApplicationContext(), R.layout.listitem_discuss);
	    
			lv.setAdapter(adapter);
			
			MessageText msg1 = new MessageText();
			msg1.setLeft(false);
			msg1.setMessage("Hello Emmanuel are you ok?");
			adapter.add(msg1);
			
			editText1 = (EditText) getActivity().findViewById(R.id.editText1);
			editText1.setOnKeyListener(new OnKeyListener() {
							@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					// If the event is a key-down event on the "enter" button
					if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
						// Perform action on key press
						MessageText msg = new MessageText();
						msg.setLeft(true);
						msg.setMessage(editText1.getText().toString());
						adapter.add(msg);
						editText1.setText("");
						return true;
					}
					return false;
				}
			});
			
		}

	}

}
