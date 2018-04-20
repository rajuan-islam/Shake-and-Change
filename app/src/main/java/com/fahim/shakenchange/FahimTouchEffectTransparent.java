package com.fahim.shakenchange;

import android.view.MotionEvent;
import android.view.View;

public class FahimTouchEffectTransparent implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_DOWN ){
            v.setBackgroundColor(Manager.TRANSPARENT_BLACK_LIGHT);
        } else if( event.getAction() == MotionEvent.ACTION_UP ){
            v.setBackgroundColor(Manager.TRANSPARENT_BLACK);
        }
        return false;
    }
}
