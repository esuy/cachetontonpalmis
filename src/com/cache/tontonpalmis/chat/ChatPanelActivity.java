package com.cache.tontonpalmis.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.chat.ChatListFragment;
import com.cache.tontonpalmis.chat.DiscussFragment;
import com.cache.tontonpalmis.chat.EmoticonsGridAdapter.KeyClickListener;
import com.cache.tontonpalmis.home.AnonsFragment;
import com.cache.tontonpalmis.profil.ContactFragment;
import com.cache.tontonpalmis.profil.Profil;
import com.cache.tontonpalmis.home.HomeFragment;
import com.cache.tontonpalmis.home.MessageFragment;

public class ChatPanelActivity extends FragmentActivity  implements KeyClickListener {
	
	
	private static final int NO_OF_EMOTICONS = 54;
	ListView listView;
	List<RowItem> rowItems;
	List<Message> messages;
	RelativeLayout lay;
	private EditText editText1;
	private DiscussArrayAdapter adapter;
	private ListView lv;
	int i = 0;
	Profil userconnect;
	Profil chat;
	private boolean isKeyBoardVisible;
	private int keyboardHeight;
	private View popUpView;
	private LinearLayout emoticonsCover;
	private PopupWindow popupWindow;
	private RelativeLayout parentLayout;
	private Bitmap[] emoticons;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//		getActionBar().hide();
		//((ActionBarActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.user);
		getActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.border_bottom_header));
		setContentView(R.layout.discuss_panel);
		
		parentLayout = (RelativeLayout) findViewById(R.id.list_parent);
		popUpView = getLayoutInflater().inflate(R.layout.emoticons_popup, null);
		emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);
		
		userconnect = new Profil();
		userconnect.setName("Emmanuel");
		
		//LinearLayout ll = this.getLayoutInflater().;
		List<Message> messages = ((ContainerMessage) getIntent().getSerializableExtra("messages")).getMessages();
		chat = ((Profil) getIntent().getSerializableExtra("profil"));
		setTitle(chat.getName());
		//setTitle(Html.fromHtml("<font color=\"red\">" + chat.getName() + "</font><br/>"+"ghygj"));
		/*setTitleColor(getResources().getColor(android.R.color.darker_gray));*/
		
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
		    TextView title = (TextView) findViewById(actionBarTitleId);
		    if (title != null) {
		        title.setTextColor(getResources().getColor(R.color.brown));
		    }
		}
		
		lv = (ListView) findViewById(R.id.listView1);
		lay = (RelativeLayout)findViewById(R.id.form);
		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);
    
		lv.setAdapter(adapter);
		
		for(Message msg:messages){
			adapter.add(msg);
		}
		editText1 = (EditText)findViewById(R.id.editText1);
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
					//if()
					//lv.setScrollY(lv.getHeight());//.scrollTo(lv., lv.getHeight());
					editText1.setText("");
					if(i==2){
						MessageText msg1 = new MessageText();
						msg1.setLeft(false);
						msg1.setMessage("Hello Emmanuel are you ok?");
						adapter.add(msg1);
						//lv.setScrollY(lv.getHeight());
						i = 0;
					}
					i = i + 1;
					return true;
				}
				return false;
			}
		});
		
		
		// Defining default height of keyboard which is equal to 230 dip
				final float popUpheight = getResources().getDimension(
						R.dimen.keyboard_height);
				changeKeyboardHeight((int) popUpheight);
		
		// Showing and Dismissing pop up on clicking emoticons button
		ImageView emoticonsButton = (ImageView) findViewById(R.id.emoticons_button);
		emoticonsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!popupWindow.isShowing()) {

					popupWindow.setHeight((int) (keyboardHeight));

					if (isKeyBoardVisible) {
						emoticonsCover.setVisibility(LinearLayout.GONE);
					} else {
						emoticonsCover.setVisibility(LinearLayout.VISIBLE);
					}
					popupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);

				} else {
					popupWindow.dismiss();
				}

			}
		});
	}

	
		
		
	public static void startUrself(Activity context) {
        Intent newActivity = new Intent(context, ChatPanelActivity.class);
        newActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newActivity);
        context.finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    
    /**
	 * Checking keyboard height and keyboard visibility
	 */
	int previousHeightDiffrence = 0;
	private void checkKeyboardHeight(final View parentLayout) {

		parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						
						Rect r = new Rect();
						parentLayout.getWindowVisibleDisplayFrame(r);
						
						int screenHeight = parentLayout.getRootView()
								.getHeight();
						int heightDifference = screenHeight - (r.bottom);
						
						if (previousHeightDiffrence - heightDifference > 50) {							
							popupWindow.dismiss();
						}
						
						previousHeightDiffrence = heightDifference;
						if (heightDifference > 100) {

							isKeyBoardVisible = true;
							changeKeyboardHeight(heightDifference);

						} else {

							isKeyBoardVisible = false;
							
						}

					}
				});

	}

	/**
	 * change height of emoticons keyboard according to height of actual
	 * keyboard
	 * 
	 * @param height
	 *            minimum height by which we can make sure actual keyboard is
	 *            open or not
	 */
	private void changeKeyboardHeight(int height) {

		if (height > 100) {
			keyboardHeight = height;
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, keyboardHeight);
			emoticonsCover.setLayoutParams(params);
		}

	}
	
	
	/**
	 * Defining all components of emoticons keyboard
	 */
	private void enablePopUpView() {

		ViewPager pager = (ViewPager) popUpView.findViewById(R.id.emoticons_pager);
		pager.setOffscreenPageLimit(3);
		
		ArrayList<String> paths = new ArrayList<String>();

		for (short i = 1; i <= NO_OF_EMOTICONS; i++) {			
			paths.add(i + ".png");
		}

		EmoticonsPagerAdapter adapter = new EmoticonsPagerAdapter(this, paths, this);
		pager.setAdapter(adapter);

		// Creating a pop window for emoticons keyboard
		popupWindow = new PopupWindow(popUpView, LayoutParams.MATCH_PARENT,
				(int) keyboardHeight, false);
		
		TextView backSpace = (TextView) popUpView.findViewById(R.id.back);
		backSpace.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
				editText1.dispatchKeyEvent(event);	
			}
		});

		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				emoticonsCover.setVisibility(LinearLayout.GONE);
			}
		});
	}

	/**
	 * Overriding onKeyDown for dismissing keyboard on key down
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (popupWindow.isShowing()) {
			popupWindow.dismiss();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}


	
	
	@Override
	public void keyClickedIndex(final String index) {
		
		ImageGetter imageGetter = new ImageGetter() {
            public Drawable getDrawable(String source) {    
            	StringTokenizer st = new StringTokenizer(index, ".");
                Drawable d = new BitmapDrawable(getResources(),emoticons[Integer.parseInt(st.nextToken()) - 1]);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };
        
        Spanned cs = Html.fromHtml("<img src ='"+ index +"'/>", imageGetter, null);        
		
		int cursorPosition = editText1.getSelectionStart();		
        editText1.getText().insert(cursorPosition, cs);
        
	}

}
