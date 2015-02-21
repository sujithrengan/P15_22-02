package com.example.rep;

import static com.example.rep.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.example.rep.CommonUtilities.SENDER_ID;

import com.example.rep.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.otto.Subscribe;












import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private static final int CENTRAL_PAGE_INDEX = 1;

	private VerticalPager mVerticalPager;
	private ListView mListView;
	private ListViewAdapter mAdapter;
	private Context mContext = this;
	private ViewGroup contactcont;
	TextView lblMessage;
	public GoogleCloudMessaging gcm;
	static String filename = "abc";
	AsyncTask<Void, Void, Void> mRegisterTask;
	private Typeface f1,f2,f3;
	
	String regId = "";
	Myreceiver mr;
	
	String msg;
	int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		findViews();
		//mListView = (ListView) findViewById(R.id.listview);
		LinearLayout container = (LinearLayout) findViewById(R.id.svg_container);
		LayoutInflater inflater = getLayoutInflater();
		addSvgView(inflater, container);

		if (savedInstanceState == null) {
			getFragmentManager()
					.beginTransaction()
					.add(R.id.container,
							new ParallaxFragment(R.drawable.space, 0))
					.commit();
		}
		if (savedInstanceState == null) {
			getFragmentManager()
					.beginTransaction()
					.add(R.id.container2,
							new ParallaxFragment(R.drawable.contact_f2, 1))
					.commit();
		}
		if (savedInstanceState == null) {
			getFragmentManager()
					.beginTransaction()
					.add(R.id.container6,
							new ParallaxFragment(R.drawable.contact_f1, 1))
					.commit();
		}
		if (savedInstanceState == null) {
			getFragmentManager()
					.beginTransaction()
					.add(R.id.container3,
							new ParallaxFragment(R.drawable.notif_f1, 1))
					.commit();
		}
		/*mAdapter = new ListViewAdapter(this);
		mListView.setAdapter(mAdapter);
		mAdapter.setMode(SwipeItemMangerImpl.Mode.Single);*/
		CommonUtilities.sp = this.getSharedPreferences("p15_history", Context.MODE_PRIVATE);
		CommonUtilities.count=CommonUtilities.sp.getInt("count",0);
		CommonUtilities.tcount=CommonUtilities.sp.getInt("tcount",0);
		f1 = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Light.ttf");
		f2 = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Medium.ttf");
		f3= Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Light.ttf");
		final Context context = this;

		ListView list = (ListView)findViewById(R.id.listview);

		
		list.addHeaderView(new View(this));
		list.addFooterView(new View(this));
		
		BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
		int count =0;
		
			int t=CommonUtilities.tcount;
		
			while(t>=1)
			{
				String mymsg=CommonUtilities.sp.getString(String.valueOf(t),"null");
				if(mymsg!="null")
				{
				CardItemData data = new CardItemData(mymsg);
				adapter.addItem(data, false);
				count++;
				if(count==5)
				{break;
				}
				}
				t--;
			}
			
		

		list.setAdapter(adapter);
		
		
		contactcont = (ViewGroup) findViewById(R.id.container_cont);
		int c=0;
		while(c<4)
		{
			addItem("Contact Name","Designation, Team","emailid@bleh.com",
					R.drawable.pp);
			c++;
		}
		gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
		mr = new Myreceiver();
		registerReceiver(mr, new IntentFilter(DISPLAY_MESSAGE_ACTION));

		mRegisterTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				if (regId.equals("")) {
					try {
						regId = gcm.register(SENDER_ID);

						storeId(regId);

					} catch (Exception e) {
					}

				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				ServerUtilities.register(context, regId);
				mRegisterTask = null;
			}

		};
		mRegisterTask.execute(null, null, null);
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Log.e("hi","hi");
				Intent intent = new Intent(MainActivity.this,
						Notify.class);
				MainActivity.this.startActivity(intent);
				// MainActivity.this.finish();
				overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
			}
		});
		
		

	}

	private void storeId(String regId) {
		final SharedPreferences prefs = getSharedPreferences(filename, 0);

		Log.i("prefs", "Saving regId on prefs ");
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("registration_id", regId);
		editor.commit();
	}

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mr);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	private void findViews() {
		mVerticalPager = (VerticalPager) findViewById(R.id.activity_main_vertical_pager);
		initViews();
	}

	private void initViews() {
		snapPageWhenLayoutIsReady(mVerticalPager, CENTRAL_PAGE_INDEX);
	}

	private void snapPageWhenLayoutIsReady(final View pageView, final int page) {
		/*
		 * VerticalPager is not fully initialized at the moment, so we want to
		 * snap to the central page only when it layout and measure all its
		 * pages.
		 */
		pageView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						mVerticalPager.snapToPage(page,
								VerticalPager.PAGE_SNAP_DURATION_INSTANT);

						if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
							// recommended removeOnGlobalLayoutListener method
							// is available since API 16 only
							pageView.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
						else
							removeGlobalOnLayoutListenerForJellyBean(pageView);
					}

					@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
					private void removeGlobalOnLayoutListenerForJellyBean(
							final View pageView) {
						pageView.getViewTreeObserver()
								.removeOnGlobalLayoutListener(this);
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		EventBus.getInstance().register(this);
	}

	@Override
	protected void onPause() {
		EventBus.getInstance().unregister(this);
		super.onPause();
	}
	
	public void notif_more(View view) {
		if (view.getId() == R.id.button1) {
			
			Log.e("hi","hi");
			Intent intent = new Intent(MainActivity.this,
					Notify.class);
			MainActivity.this.startActivity(intent);
			// MainActivity.this.finish();
			overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
		}
	}

	@Subscribe
	public void onLocationChanged(PageChangedEvent event) {
		mVerticalPager.setPagingEnabled(event.hasVerticalNeighbors());
	}
private void addItem(String name,String desig,String email,int t) {

	final ViewGroup newView = (ViewGroup) LayoutInflater.from(this)
				.inflate(R.layout.list_row, contactcont, false);
		TextView T;
		TextView T2;
		TextView T3;
		TextView T4;
		
		ImageView I1;
		T = (TextView) newView.findViewById(R.id.title);
		T2 = (TextView) newView.findViewById(R.id.desig);
		T3 = (TextView) newView.findViewById(R.id.email);
		T4 = (TextView) newView.findViewById(R.id.slide);
		I1= (ImageView) newView.findViewById(R.id.thumbnail);

		T2.setText(desig);
		I1.setBackgroundResource(t);
		T.setText(name);
		T3.setText(email);

		T4.setText("");
		
		T.setTypeface(f2);
		T3.setTypeface(f1);
		T2.setTypeface(f1);
		newView.findViewById(R.id.call).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Log.e("call","called");
						Intent intent = new Intent(Intent.ACTION_DIAL);
						intent.setData(Uri.parse("tel:0123456789"));
						startActivity(intent);
					}
				});
		newView.findViewById(R.id.text).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Log.e("call","called");
						Intent sendIntent = new Intent(Intent.ACTION_VIEW);         
						sendIntent.setData(Uri.parse("sms:123456789"));					
						startActivity(sendIntent);
					}
				});

		

		contactcont.addView(newView, 0);

	}
	private void addSvgView(LayoutInflater inflater, LinearLayout container) {
		final View view = inflater.inflate(R.layout.item_svg, container, false);
		final SvgView svgView = (SvgView) view.findViewById(R.id.svg);

		svgView.setSvgResource(R.raw.map_pr);
		// view.setBackgroundResource(R.color.accent);
		svgView.setmCallback(new SvgCompletedCallBack() {
			ProgressView p = (ProgressView) findViewById(R.id.progress);
			TextView t = (TextView) findViewById(R.id.textView2);

			@Override
			public void onSvgCompleted() {
				p.setVisibility(view.INVISIBLE);
				t.setVisibility(view.VISIBLE);

			}
		});

		container.addView(view);

		Handler handlerDelay = new Handler();
		handlerDelay.postDelayed(new Runnable() {
			public void run() {
				svgView.startAnimation();
			}
		}, 2000);
	}

	/*public void asg(String msg1, int count1) {
		msg=msg1;
		count=count1;
		
	}*/

}