package com.example.rep;

import com.example.rep.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
//import fr.northborders.AnimationPlayground.R;

/**
 * Created by thibaultguegan on 29/05/2014.
 */
public class DrawPathSvgActivity extends Activity {
	
	private Button DoAgainBtn;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.svg_activity);

        LinearLayout container = (LinearLayout) findViewById(R.id.svg_container);
        LayoutInflater inflater = getLayoutInflater();
        addSvgView(inflater, container);
    }

    private void addSvgView(LayoutInflater inflater, LinearLayout container)
    {
        final View view = inflater.inflate(R.layout.item_svg, container, false);
        final SvgView svgView = (SvgView) view.findViewById(R.id.svg);

        svgView.setSvgResource(R.raw.map_pr);
        //view.setBackgroundResource(R.color.accent);
       
        
        container.addView(view);
       

        Handler handlerDelay = new Handler();
        handlerDelay.postDelayed(new Runnable(){
            public void run() {
                svgView.startAnimation();
            }}, 2000);
    }
}