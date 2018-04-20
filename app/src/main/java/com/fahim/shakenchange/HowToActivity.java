package com.fahim.shakenchange;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;


public class HowToActivity extends ActionBarActivity {

    RelativeLayout page_1, page_2, page_3, page_4, page_5;
    HorizontalScrollView how_to_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        // action bar modifications
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        page_1 = (RelativeLayout) findViewById(R.id.page_1);
        page_2 = (RelativeLayout) findViewById(R.id.page_2);
        page_3 = (RelativeLayout) findViewById(R.id.page_3);
        page_4 = (RelativeLayout) findViewById(R.id.page_4);
        page_5 = (RelativeLayout) findViewById(R.id.page_5);

        how_to_scroll = (HorizontalScrollView) findViewById(R.id.how_to_scroll);
        how_to_scroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = getWindowManager().getDefaultDisplay().getWidth();

                page_1.getLayoutParams().width=width;
                page_1.requestLayout();

                page_2.getLayoutParams().width=width;
                page_2.requestLayout();

                page_3.getLayoutParams().width=width;
                page_3.requestLayout();

                page_4.getLayoutParams().width=width;
                page_4.requestLayout();

                page_5.getLayoutParams().width=width;
                page_5.requestLayout();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_how_to, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
