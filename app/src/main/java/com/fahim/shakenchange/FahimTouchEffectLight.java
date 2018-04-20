package com.fahim.shakenchange;

import android.view.MotionEvent;
import android.view.View;

public class FahimTouchEffectLight implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_DOWN ){
            v.setBackgroundColor(Manager.TRANSPARENT_PRESSED);
        } else if( event.getAction() == MotionEvent.ACTION_UP ){
            v.setBackgroundColor(Manager.TRANSPARENT);
        }
        return false;
    }
}
