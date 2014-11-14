package com.cache.tontonpalmis;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.cache.tontonpalmis.chat.ChatListFragment;
import com.cache.tontonpalmis.clientweb.TontonPalmisRestClient;
import com.cache.tontonpalmis.home.AnonsFragment;
import com.cache.tontonpalmis.profil.ContactFragment;
import com.cache.tontonpalmis.profil.Profil;
import com.cache.tontonpalmis.service.DataReceiver;
import com.cache.tontonpalmis.home.HomeFragment;
import com.cache.tontonpalmis.home.MessageFragment;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends ActionBarActivity {
	public static final String M_CURRENT_TAB = "M_CURRENT_TAB";
	private TabHost mTabHost;
	private String mCurrentTab;

	public static final String TAB_HOME = "TAB_HOME";
	public static final String TAB_MSG = "TAB_MSG";
	public static final String TAB_ANONS = "TAB_ANONS";
	public static final String TAB_CHAT = "TAB_CHAT";
	public static final String TAB_CONTACT = "TAB_CONTACT";
	View view1;
	View view2;
	View view3;
	View view4;
	View view5;
	Profil profil;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		
		getSupportActionBar().hide();
		setContentView(R.layout.activity_main);
		setupTabPan(savedInstanceState);
		profil = (Profil)this.getIntent().getSerializableExtra("profil");
		/*Intent serviceIntent = new Intent();
		serviceIntent.setAction("com.cache.tontonpalmis.service.DataReceiver");
		startService(serviceIntent);*/
		
		startService(new Intent(this, DataReceiver.class));
		
	}
	
	
	
    private void setupTabPan(Bundle savedInstanceState){
    	try {
			mTabHost = (TabHost) findViewById(android.R.id.tabhost);
			mTabHost.setup();

			if (savedInstanceState != null) {
				mCurrentTab = savedInstanceState.getString(M_CURRENT_TAB);
				initializeTabs();
				mTabHost.setCurrentTabByTag(mCurrentTab);
				/*
				 * when resume state it's important to set listener after
				 * initializeTabs
				 */
				mTabHost.setOnTabChangedListener(listener);
			} else {
				mTabHost.setOnTabChangedListener(listener);
				initializeTabs();
			}
		} catch (Exception ex) {
			if(ex instanceof NullPointerException){
				NullPointerException n = (NullPointerException) ex;
				StackTraceElement stackTrace = n.getStackTrace()[0];
				Toast.makeText(
						getApplicationContext(),
						ex.toString() + " " + "Unexpected Exception due at "
								+ stackTrace.getLineNumber(), 1000000).show();
			}else{
				Toast.makeText(
						getApplicationContext(),
						ex.getMessage(), 1000000).show();
			}
		}

    }
	private View createTabView(final int id, final String text,
			final String textValue) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
		imageView.setImageDrawable(getResources().getDrawable(id));
		TextView textView = (TextView) view.findViewById(R.id.tab_text);
		textView.setText(text);
		TextView textViewval = (TextView) view.findViewById(R.id.tab_num);
		textViewval.setText(textValue);
		return view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			this.finish();//try activityname.finish instead of this
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void initializeTabs() {
		try {
			TabHost.TabSpec spec;
			/******************************** Initioalisation Home tab **************************/
			spec = mTabHost.newTabSpec(TAB_HOME);
			spec.setContent(new TabHost.TabContentFactory() {
				public View createTabContent(String tag) {
					return findViewById(R.id.realtabcontent);
				}
			});
			view1 = createTabView(R.drawable.home, "Akey", "");
			view1.setBackgroundColor(getResources().getColor(R.color.orange01));
			spec.setIndicator(view1);
			mTabHost.addTab(spec);
			spec = null;
			/****
			 * ---------------- fin Initioalisation Home
			 * tab------------------------
			 ****/

			/******************************** Initioalisation MSG tab **************************/
			spec = mTabHost.newTabSpec(TAB_MSG);
			spec.setContent(new TabHost.TabContentFactory() {
				public View createTabContent(String tag) {
					return findViewById(R.id.realtabcontent);
				}
			});
			view2 = createTabView(R.drawable.chat, "Mesaj", "1");
			view2.setBackgroundColor(getResources().getColor(R.color.brown));
			spec.setIndicator(view2);
			mTabHost.addTab(spec);
			/****
			 * ---------------- fin Initioalisation MSG
			 * tab------------------------
			 ****/

			/******************************** Initioalisation ANONS tab **************************/
			spec = mTabHost.newTabSpec(TAB_ANONS);
			spec.setContent(new TabHost.TabContentFactory() {
				public View createTabContent(String tag) {
					return findViewById(R.id.realtabcontent);
				}
			});
			view3 = createTabView(R.drawable.anons, "Anons", "52");
			view3.setBackgroundColor(getResources().getColor(R.color.brown));
			spec.setIndicator(view3);
			mTabHost.addTab(spec);
			/****
			 * ---------------- fin Initioalisation ANONS
			 * tab------------------------
			 ****/

			/******************************** Initioalisation CHAT tab **************************/
			spec = mTabHost.newTabSpec(TAB_CHAT);
			spec.setContent(new TabHost.TabContentFactory() {
				public View createTabContent(String tag) {
					return findViewById(R.id.realtabcontent);
				}
			});
			view4 = createTabView(R.drawable.chat, "Chat", "0");
			view4.setBackgroundColor(getResources().getColor(R.color.brown));
			spec.setIndicator(view4);
			mTabHost.addTab(spec);
			/****
			 * ---------------- fin Initioalisation CHAT
			 * tab------------------------
			 ****/

			/******************************** Initioalisation CONTACT tab **************************/
			spec = mTabHost.newTabSpec(TAB_CONTACT);
			spec.setContent(new TabHost.TabContentFactory() {
				public View createTabContent(String tag) {
					return findViewById(R.id.realtabcontent);
				}
			});
			view5 = createTabView(R.drawable.contact, "Kontak", "0");
			view5.setBackgroundColor(getResources().getColor(R.color.brown));
			spec.setIndicator(view5);
			mTabHost.addTab(spec);
			/****
			 * ---------------- fin Initioalisation CONTACT
			 * tab------------------------
			 ****/

		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), ex.getMessage(),
					Toast.LENGTH_LONG).show();
		}

	}

	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
		boolean tab1 = false;
		boolean tab2 = false;
		boolean tab3 = false;
		boolean tab4 = false;
		boolean tab5 = false;

		public void onTabChanged(String tabId) {

			mCurrentTab = tabId;

			if (tabId.equals(TAB_HOME)) {
				tab1 = true;
				if (tab2) {
					view2.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab2 = false;
				}
				if (tab3) {
					view3.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab3 = false;
				}
				if (tab4) {
					view4.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab4 = false;
				}
				if (tab5) {
					view5.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab5 = false;
				}
				pushFragments(new HomeFragment(), false, false, null);
				view1.setBackgroundColor(getResources().getColor(
						R.color.orange01));
			} else if (tabId.equals(TAB_MSG)) {
				tab2 = true;
				if (tab1) {
					view1.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab1 = false;
				}
				if (tab3) {
					view3.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab3 = false;
				}
				if (tab4) {
					view4.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab4 = false;
				}
				if (tab5) {
					view5.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab5 = false;
				}
				pushFragments(new MessageFragment(), false, false, null);
				view2.setBackgroundColor(getResources().getColor(
						R.color.orange01));
			} else if (tabId.equals(TAB_ANONS)) {
				tab3 = true;
				if (tab1) {
					view1.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab1 = false;
				}
				if (tab2) {
					view2.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab2 = false;
				}
				if (tab4) {
					view4.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab4 = false;
				}
				if (tab5) {
					view5.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab5 = false;
				}
				pushFragments(new AnonsFragment(), false, false, null);
				view3.setBackgroundColor(getResources().getColor(
						R.color.orange01));
			} else if (tabId.equals(TAB_CHAT)) {
				tab4 = true;
				if (tab1) {
					view1.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab1 = false;
				}
				if (tab3) {
					view3.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab3 = false;
				}
				if (tab2) {
					view2.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab2 = false;
				}
				if (tab5) {
					view5.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab5 = false;
				}
				pushFragments(new ChatListFragment(), false, false, null);
				view4.setBackgroundColor(getResources().getColor(
						R.color.orange01));
			} else if (tabId.equals(TAB_CONTACT)) {
				tab5 = true;
				if (tab1) {
					view1.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab1 = false;
				}
				if (tab3) {
					view3.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab3 = false;
				}
				if (tab2) {
					view2.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab2 = false;
				}
				if (tab4) {
					view4.setBackgroundColor(getResources().getColor(
							R.color.brown));
					tab4 = false;
				}
				pushFragments(new ContactFragment(), false, false, null);
				view5.setBackgroundColor(getResources().getColor(
						R.color.orange01));
			}

		}
	};

	public void pushFragments(Fragment fragment, boolean shouldAnimate,
			boolean shouldAdd, String tag) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		if (shouldAnimate) {
			ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
					R.animator.fragment_slide_left_exit,
					R.animator.fragment_slide_right_enter,
					R.animator.fragment_slide_right_exit);
		}
		ft.replace(R.id.realtabcontent, fragment, tag);

		if (shouldAdd) {
			/*
			 * here you can create named backstack for realize another logic.
			 * ft.addToBackStack("name of your backstack");
			 */
			ft.addToBackStack(null);
		} else {
			/*
			 * and remove named backstack:
			 * manager.popBackStack("name of your backstack",
			 * FragmentManager.POP_BACK_STACK_INCLUSIVE); or remove whole:
			 * manager.popBackStack(null,
			 * FragmentManager.POP_BACK_STACK_INCLUSIVE);
			 */
			manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		ft.commit();
	}

	public static void startUrself(Activity context) {
		Intent newActivity = new Intent(context, MainActivity.class);
		newActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(newActivity);
		context.finish();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString(M_CURRENT_TAB, mCurrentTab);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	

}
