package com.cache.tontonpalmis.profil;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cache.tontonpalmis.IntroActivity;
import com.cache.tontonpalmis.MainActivity;
import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.R.drawable;
import com.cache.tontonpalmis.clientweb.TontonPalmisRestClient;
import com.cache.tontonpalmis.db.DatabaseManager;
import com.cache.tontonpalmis.util.Util;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class ContactFragment extends Fragment implements OnItemClickListener{
	View view;
	ListView listView;
	LinearLayout layInfoUser;
	RelativeLayout banner;
	List<RowItem> rowItems;
	List<Profil> profils;
	CustomListViewAdapter adapter;
	private BaseCoverFlow coverFlow;
	DatabaseManager db;
	Profil profil;
	private ServiceConnection mConnection = new ServiceConnection() {
	      
		/*public void onServiceConnected(ComponentName className, IBinder service) {          
            imService = ((IMService.IMBinder)service).getService();          
        }
        public void onServiceDisconnected(ComponentName className) {          
        	imService = null;
            Toast.makeText(Messaging.this, R.string.local_service_stopped,
                    Toast.LENGTH_SHORT).show();
        }*/
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(ContactFragment.this.getActivity(), "Internet disconnected",
                    Toast.LENGTH_SHORT).show();
			
		}
    };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		db = new DatabaseManager(this.getActivity());
		profil = db.getProfilByTel(db.getNumero(Util.getSerial(this.getActivity())).trim());
		//profils = db.getProfils(profil.getId());
		new LoadViewTask().execute();
		
		// listView.setOnItemClickListener(this.getActivity().getApplication());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// view = super.onCreateView(inflater, container, savedInstanceState);
		view = (RelativeLayout) inflater.inflate(R.layout.contact_fragment, container, false);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	void serachUI(){
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
	        // use SearchView
	    	SearchView search = new SearchView(this.getActivity());
	    	RelativeLayout rel = (RelativeLayout)this.getActivity().findViewById(R.id.banner);
	    	Log.i("class", this.getActivity().findViewById(R.id.banner).getClass().getName());
	    	LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);;
	    	params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	        params.addRule(RelativeLayout.CENTER_VERTICAL);
	        params.rightMargin = 10;
	    	rel.addView(search, params);
	    	int searchPlateId = search.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
	        View searchPlate = search.findViewById(searchPlateId);
	        android.widget.LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);;
	        params1.leftMargin = 100;
	        //	    	params1..addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//	        params1.addRule(RelativeLayout.CENTER_VERTICAL);
	        
	        searchPlate.setLayoutParams(params1);

	    	
	    	search.setOnQueryTextListener(new OnQueryTextListener(){

				@Override
				public boolean onQueryTextSubmit(String query) {
					// TODO Auto-generated method stub
					new OnlineSearchContactProcessing(query).search();
					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					// TODO Auto-generated method stub
					return false;
				}
	    		
	    	});
	    } else {
	        // use some other backward compatible custom view
	    }
	}
	private class OnlineSearchContactProcessing {
		String query;
		JSONObject response;
		public OnlineSearchContactProcessing(String query){
			this.query = query;
		}
		public void search(){
			RequestParams params = new RequestParams();
			params.put("numero", this.query);
			Log.i("query", this.query);
			TontonPalmisRestClient.get("getProfilByTel", params, new JsonHttpResponseHandler(){
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseString, throwable);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONArray errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("responsesearch", response.toString());
					OnlineSearchContactProcessing.this.response = response;
					parsingResponse(response);
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
					Log.i("finish","finish...");
//					if(response!=null)
//						parsingResponse(response);
				}
			});
		}
		
		private void parsingResponse(JSONObject response){
			if(!response.isNull("success")){
				try {
					if(response.getBoolean("success")){
						if(!response.isNull("data")){
							Profil profil;
							JSONObject data = response.getJSONObject("data");
							profil = Profil.parseJson(data);
							profil.setFriends(false);
							List<RowItem> items = new ArrayList<RowItem>();
							items.add(new RowItem(profil));
							adapter.setItemsList(items);
							listView.setAdapter(adapter);
							listView.setOnItemClickListener(ContactFragment.this);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
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
					profils = db.getProfils(profil.getId());
					rowItems = new ArrayList<RowItem>();
//					Toast.makeText(getActivity().getApplicationContext(),
//							messages.size()+"", 100000).show();
					for (int i = 0; i < profils.size(); i++) {
						// RowItem item = new
						// RowItem(LoadImageFromWebOperations(countries.get(i).getSymbole()),
						// countries.get(i).getName(), "Description");
						RowItem item = new RowItem((Profil)profils.get(i));
						rowItems.add(item);
					}
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
			
			adapter = new CustomListViewAdapter(
					getActivity(), R.layout.list_item_contact, rowItems);
			listView = (ListView)getActivity().findViewById(R.id.listProfil);
			banner = (RelativeLayout)getActivity().findViewById(R.id.banner);
			serachUI();
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(ContactFragment.this);
			
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//view.setBackgroundResource(R.color.clicklist);
		
		Profil profil = adapter.getItem(position).getProfil();
		//Toast.makeText(this.getActivity(), profil.isFriends()+"..."+profil.isWaitting(), 100000);
		Log.i("prifil info", profil.isFriends()+"..."+profil.isWaitting());
		banner.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
		
		designpanelInfo(profil);
		
        layInfoUser.setVisibility(View.VISIBLE);
	}
	
	private void designpanelInfo(final Profil profil){
		layInfoUser = (LinearLayout)getActivity().findViewById(R.id.detailsUser);
		coverFlow = (BaseCoverFlow) getActivity().findViewById(R.id.coverFlow);
		Log.i("size adapter", adapter.getCount()+"");
		ImageView imageViewBack = (ImageView) layInfoUser.findViewById(R.id.imageViewBack);
		imageViewBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layInfoUser.setVisibility(View.GONE);
				
				banner.setVisibility(View.VISIBLE);
				listView.setVisibility(View.VISIBLE);
			}
		});
		
		TextView tvNameProfil = (TextView) layInfoUser.findViewById(R.id.tvNameProfil);
		tvNameProfil.setText(profil.getName());
		TextView tvprofil_icon = (TextView) layInfoUser.findViewById(R.id.tvprofil_icon);
		
		if(!profil.isFriends() && !profil.isWaitting()){
			Drawable img = this.getActivity().getResources().getDrawable(android.R.drawable.ic_input_add);
			img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
			tvprofil_icon.setCompoundDrawables(img, null, null, null);
		}
		
		if(profil.isWaitting()){
			Drawable img = this.getActivity().getResources().getDrawable(android.R.drawable.ic_input_add);
			img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
			tvprofil_icon.setCompoundDrawables(img, null, null, null);
		}
		tvprofil_icon.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!profil.isFriends() && !profil.isWaitting())
					addContact(ContactFragment.this.profil.getId(),profil.getId());
				if(profil.isWaitting())
					acceptContact(ContactFragment.this.profil.getId(),profil.getId());
				    
			}
			
		});
		
		
		TextView tvTelPhone = (TextView) layInfoUser.findViewById(R.id.tvTelPhone);
		tvTelPhone.setText(" "+Util.phone(profil.getTel()));
		
		TextView tvZoneContact = (TextView) layInfoUser.findViewById(R.id.tvZoneContact);
		tvZoneContact.setText(profil.getZone());
		
        this.coverFlow.setAdapter(new BaseCoverFlowProfilAdapter());
        this.coverFlow.setUnselectedAlpha(1.0f);
        this.coverFlow.setUnselectedSaturation(0.0f);
        this.coverFlow.setUnselectedScale(0.5f);
        this.coverFlow.setSpacing(5);
        this.coverFlow.setMaxRotation(0);
        this.coverFlow.setScaleDownGravity(0.2f);
        this.coverFlow.setActionDistance(BaseCoverFlow.ACTION_DISTANCE_AUTO);
	}
	
	public void refreshContact(long id){
		RequestParams params = new RequestParams();
		params.put("id", id);
		TontonPalmisRestClient.get("getContactProfil", params,new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("response", response.toString());
				try {
					if(response.getBoolean("success")){
						if(!response.isNull("data")){
							JSONArray array = response.getJSONArray("data");
							for(int i=0;i<array.length();i++){
								Profil profil = Profil.parseJson(array.getJSONObject(i));
								profil.setFriends(true);
								db.insertProfil(profil);
							}
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	});
	}
	
	
	private void addContact(long ownerID,long friendID){
		RequestParams params = new RequestParams();//friendID=2&ownerID=7
		params.put("ownerID", ownerID);
		params.put("friendID", friendID);
		Log.i("parametres", "?ownerID="+ownerID+"&&friendID="+friendID);
		TontonPalmisRestClient.get("addContact", params,new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("response", response.toString());
				try {
					if(response.getBoolean("success")){
						//refreshContact(profil.getId());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	});
	}
	
	private void acceptContact(long friendID,long ownerID){
		RequestParams params = new RequestParams();//friendID=2&ownerID=7
		params.put("ownerID", ownerID);
		params.put("friendID", friendID);
		TontonPalmisRestClient.get("acceptContact", params,new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("response", response.toString());
				try {
					if(response.getBoolean("success")){
						//refreshContact(profil.getId());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	});
	}

}
