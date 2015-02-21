package com.example.rep;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Notify extends Activity {
	Bundle got;
	String[] sub;
	static int count = 0;
	String msg;
	static int counter=0;
	static int k=0;


	int pos;
	private ViewGroup mContainerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notif_layout);
		if (savedInstanceState == null) {
			getFragmentManager()
					.beginTransaction()
					.add(R.id.container4,
							new ParallaxFragment(R.drawable.contact_f2, 1))
					.commit();
		}
		pos = 1;
		// notif=new String[10];

		//got = getIntent().getExtras();

		/*
		 * proceed.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 */

		/*if (got != null) {

			msg = got.getString("key");
			//CommonUtilities.sp=this.getSharedPreferences("p15_history",Context.MODE_PRIVATE);

			CommonUtilities.count++;
			Editor et=CommonUtilities.sp.edit();
			et.putString(String.valueOf(CommonUtilities.count), msg);
			et.putInt("count",CommonUtilities.count);
			et.commit();
			
			Log.d("counter",String.valueOf(counter));
		}*/
		/*if (Myreceiver.Notiftext!= null) {
			
			//msg = got.getString("key");
			//CommonUtilities.sp=this.getSharedPreferences("p15_history",Context.MODE_PRIVATE);

			CommonUtilities.count++;
			Editor et=CommonUtilities.sp.edit();
			et.putString(String.valueOf(CommonUtilities.count),Myreceiver.Notiftext);
			et.putInt("count",CommonUtilities.count);
			et.commit();
			
			Log.d("counter",String.valueOf(counter));
			Myreceiver.Notiftext= null;
		}*/

		mContainerView = (ViewGroup) findViewById(R.id.container);

		findViewById(android.R.id.empty).setVisibility(View.GONE);

		int t=1;
		while(t<=CommonUtilities.tcount)
		{
			String mymsg=CommonUtilities.sp.getString(String.valueOf(t),"null");
			if(mymsg!="null")
			{addItem(mymsg,t);}
			t++;
		}
	}
	

	private void addItem(String s,int t) {

		// TextView t = (TextView) findViewById(R.id.txt_event);

		// Instantiate a new "row" view.

		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this)
				.inflate(R.layout.listview_item, mContainerView, false);
		TextView T;
		TextView T2;

		// timestamp = DateFormat.getDateTimeInstance().format(new Date());

		// Set the text in the new row to a random country.
		T = (TextView) newView.findViewById(R.id.notif_text);
		T2 = (TextView) newView.findViewById(R.id.position);
		// LinearLayout L=(LinearLayout)newView.findViewById(R.id.llayout1);

		T2.setText(String.valueOf(t));

		T.setText(s);

		// Set a click listener for the "X" button in the row that will remove
		// the row.
		newView.findViewById(R.id.trash).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// Remove the row from its parent (the container view).
						// Because mContainerView has
						// android:animateLayoutChanges set to true,
						// this removal is automatically animated.
						TextView t=(TextView) newView.findViewById(R.id.position);
						String t1=t.getText().toString();
						Editor et=CommonUtilities.sp.edit();
						et.remove(t1);
						CommonUtilities.count--;
						et.putInt("count",CommonUtilities.count);
						et.commit();
						mContainerView.removeView(newView);

						// If there are no rows remaining, show the empty view.
						if (mContainerView.getChildCount() == 0) {
							findViewById(android.R.id.empty).setVisibility(
									View.VISIBLE);
						}
					}
				});
		newView.findViewById(R.id.delitem).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// Remove the row from its parent (the container view).
						// Because mContainerView has
						// android:animateLayoutChanges set to true,
						// this removal is automatically animated.
						TextView t=(TextView) newView.findViewById(R.id.position);
						String t1=t.getText().toString();
						Editor et=CommonUtilities.sp.edit();
						et.remove(t1);
						CommonUtilities.count--;
						et.putInt("count",CommonUtilities.count);
						et.commit();
						mContainerView.removeView(newView);

						// If there are no rows remaining, show the empty view.
						if (mContainerView.getChildCount() == 0) {
							findViewById(android.R.id.empty).setVisibility(
									View.VISIBLE);
						}
					}
				});

		// Because mContainerView has android:animateLayoutChanges set to true,
		// adding this view is automatically animated.
		// mContainerView.add

		mContainerView.addView(newView, 0);

	}
	
	
	
}
