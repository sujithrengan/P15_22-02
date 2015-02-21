package com.example.rep;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;

final class Touch implements OnTouchListener {

	public boolean onTouch(View view, MotionEvent motionEvent) {

		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			// setup drag

			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
					view);
			view.startDrag(data, shadowBuilder, view, 0);
			return true;
		} else {
			return false;
		}
	}

}