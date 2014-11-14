package com.cache.tontonpalmis.chat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.R.drawable;
import com.cache.tontonpalmis.profil.Profil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Point;

public class ChatListFragment extends Fragment implements OnItemClickListener{
	View view;
	ListView listView;
	List<RowItem> rowItems;
	List<RowItem> rowItemSm;
	List<Message> messages;
    Profil userconnect;
    CustomListViewAdapter adapter;
    private PopupWindow popWindow;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		userconnect = new Profil();
		userconnect.setName("Emmanuel");
		new LoadViewTask().execute();

		

		// listView.setOnItemClickListener(this.getActivity().getApplication());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// view = super.onCreateView(inflater, container, savedInstanceState);
		view = (RelativeLayout) inflater.inflate(R.layout.chatlist_fragment,
				container, false);
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
				try {
					rowItems = new ArrayList<RowItem>();
					RowItem item;
					messages = new ArrayList<Message>();
					MessageText msg1 = new MessageText();
					Profil p1 = new Profil();
					p1.setName("Cornet");
					p1.setZone("PAP");
					msg1.setSender(p1);
					msg1.setReceiver(userconnect);
					msg1.setMessage("Yow man");
					msg1.setLeft(false);
					msg1.setTime("Today");
					List<Message> lmsg = new ArrayList<Message>();
					lmsg.add(msg1);
					item = new RowItem(lmsg,p1);
					//messages.add(msg1);
					rowItems.add(item);
					if(rowItems.size()>3)
						rowItemSm = rowItems.subList(0, 2);
					else
						rowItemSm = rowItems;
//					messages.add(msg2);
					
					
//					Toast.makeText(getActivity().getApplicationContext(),
//							messages.size()+"", 100000).show();
					/*for (int i = 0; i < messages.size(); i++) {
						// RowItem item = new
						// RowItem(LoadImageFromWebOperations(countries.get(i).getSymbole()),
						// countries.get(i).getName(), "Description");
						
						RowItem item = new RowItem(messages.get(i));
						Log.i("Add message", "ok...");
						rowItems.add(item);
					}*/
				} catch (Exception ex) {
					// showDialog("Failed","Retry loading");
					// this.
				}

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
			listView = (ListView)getActivity().findViewById(R.id.listChat);
			adapter = new CustomListViewAdapter(userconnect,
					getActivity(), R.layout.list_item_chat, rowItems);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(ChatListFragment.this);
			
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity().getApplicationContext(),
		/*List<Message> messages = new ArrayList<Message>();
		messages.add(adapter.getItem(position).getMessage());
		Profil profil = adapter.getItem(position).getProfil();
		Intent i = new Intent(this.getActivity().getApplicationContext(),ChatPanelActivity.class);
		i.putExtra("messages", new ContainerMessage(messages));
		i.putExtra("profil", profil);
	    this.startActivity(i);*/
		onShowPopup(this.getView());
	}
	
	// call this method when required to show popup
    @SuppressLint("NewApi")
	public void onShowPopup(View v){
 
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.chat_msg_popup, null,false);
        // find the ListView in the popup layout
        ListView listView = (ListView)inflatedView.findViewById(R.id.commentsListView);
 
        // get device size
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        //	mDeviceHeight = size.y;
 
 
        // fill the data to the list items
        setSimpleList(listView);
 
 
        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, size.x - 50,size.y - 100, false );
        // set a background drawable with rounders corners
        //popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.fb_popup_bg));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);
 
        // show the popup at bottom of the screen and set some margin at bottom ie,
        //popWindow.showAtLocation(v, Gravity.BOTTOM, 0,100);
        popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }
    
    
    void setSimpleList(ListView listView){
        
        ArrayList<String> contactsList = new ArrayList<String>();
 
        for (int index = 0; index < 10; index++) {
            contactsList.add("I am @ index " + index + " today " + Calendar.getInstance().getTime().toString());
        }
 
//        listView.setAdapter(new ArrayAdapter<String>(TryMeActivity.this,
//            R.layout.fb_comments_list_item, android.R.id.text1,contactsList));
    }

}
