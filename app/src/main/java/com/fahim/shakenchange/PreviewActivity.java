package com.fahim.shakenchange;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;


public class PreviewActivity extends Activity {

    ImageView previewImage;
    HorizontalScrollView previewScroll;
    Button setButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        // setting image
        previewImage = (ImageView) findViewById(R.id.previewImage);
        Container item = Manager.picList.get(Manager.currentPreview);
        previewImage.setImageResource(item.imageId);

        // setting button
        setButton = (Button) findViewById(R.id.setButton);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manager.currentSet = Manager.currentPreview;
                Container pic = Manager.picList.get(Manager.currentSet);
                try {
                    Bitmap wallpaper = BitmapFactory.decodeStream(getResources().openRawResource(pic.imageId));
                    getApplicationContext().setWallpaper(wallpaper);
                    Toast.makeText(PreviewActivity.this, "Wallpaper successfully set.", Toast.LENGTH_LONG).show();
                    Manager.savePref(PreviewActivity.this);
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(PreviewActivity.this,"Error occurred!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        setButton.setOnTouchListener(new FahimTouchEffectTransparent());

        // setting scrollview to center
        previewScroll = (HorizontalScrollView) findViewById(R.id.previewScroll);
        previewScroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int childWidth = previewScroll.getChildAt(0).getWidth();
                int parentWidth = previewScroll.getWidth();
                previewScroll.scrollTo( (childWidth/2) - (parentWidth/2) ,0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preview, menu);
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
