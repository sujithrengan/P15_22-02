package com.example.rep;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

import com.example.rep.R;
import com.example.rep.ParallaxImageView;

public class ParallaxFragment extends Fragment {

    private ParallaxImageView mBackground;

    private int bpic,screen;
    private int mCurrentImage;
    private boolean mParallaxSet = true;
    private boolean mPortraitLock = true;
    private TextView tv;

    public ParallaxFragment(int pic,int screen) {
    	
    	bpic=pic;
    	this.screen=screen;
    	
    	
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parallax, container, false);
        if (rootView == null) return null;


        mBackground = (ParallaxImageView) rootView.findViewById(R.id.background);
        mBackground.setImageBitmap(BitmapFactory.decodeResource(getResources(), bpic));


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) getView().findViewById(R.id.welcome);
    	if(screen!=0){
    		tv.setVisibility(View.INVISIBLE);
    	}
        // Adjust the Parallax forward tilt adjustment
        mBackground.setForwardTiltOffset(.35f);
        mBackground.setParallaxIntensity(1f + ((float) 7) / 40);



    }

    @Override
    public void onResume() {
        super.onResume();

        if (mParallaxSet)
            mBackground.registerSensorManager();
    }

    @Override
    public void onPause() {
        mBackground.unregisterSensorManager();
        super.onPause();
    }
   

}