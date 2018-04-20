package com.fahim.shakenchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class LauncherActivity extends Activity {

    Handler handler;
    Runnable runnable;
    TextView prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        prompt = (TextView)findViewById(R.id.prompt);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(LauncherActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
                //prompt.setVisibility(View.VISIBLE);
                Intent intent = new Intent(LauncherActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        };
        handler.postDelayed(runnable,1500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
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

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
