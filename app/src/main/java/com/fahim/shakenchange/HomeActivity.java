package com.fahim.shakenchange;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class HomeActivity extends ActionBarActivity {

    LinearLayout creditButton, howToButton, settingsButton, selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_2);

        // action bar modifications
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        creditButton = (LinearLayout) findViewById(R.id.creditButton);
        howToButton = (LinearLayout) findViewById(R.id.howToButton);
        settingsButton = (LinearLayout) findViewById(R.id.settingsButton);
        selectButton = (LinearLayout) findViewById(R.id.selectButton);
        manageButtons();

        // load settings from preferences
        Manager.loadPref(this);

        // stop sensor wallpaper change
        Manager.INSIDE_APP = true;
        Log.i(Manager.FTAG,"HomeActivity: started");
        Intent intent = new Intent(HomeActivity.this,FahimWallpaperService.class);
        boolean find = stopService(intent);
        if(find) Log.i(Manager.FTAG,"HomeActivity: Service found and stopped");
        else Log.i(Manager.FTAG,"HomeActivity: Service not found");
    }

    // SETTING UP BUTTON LISTENERS
    public void manageButtons(){
        creditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreditActivity.class);
                startActivity(intent);
            }
        });
        creditButton.setOnTouchListener(new FahimTouchEffectLight());

        howToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomeActivity.this, HowToActivity.class );
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "Scroll to right or left.", Toast.LENGTH_LONG).show();
            }
        });
        howToButton.setOnTouchListener(new FahimTouchEffectLight());

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
        settingsButton.setOnTouchListener(new FahimTouchEffectLight());

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,SelectActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "Tap on an image for larger preview.", Toast.LENGTH_LONG).show();
            }
        });
        selectButton.setOnTouchListener(new FahimTouchEffectLight());
    }

    // OVERRIDING BACK BUTTON
    @Override
    public void onBackPressed() {
        final Dialog quit_dialog = new Dialog(HomeActivity.this);
        quit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quit_dialog.setContentView(R.layout.dialog_quit);
        quit_dialog.setCancelable(false);

        ActivityManager.RunningServiceInfo ser;

        // SETTING BUTTON LISTENERS
        Button button = (Button) quit_dialog.findViewById(R.id.yesButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit_dialog.dismiss();
                Manager.INSIDE_APP = false;

                Log.i(Manager.FTAG, "HomeActivity: Quit app");
                if( Manager.IS_SENSOR_ACTIVE ){
                    Log.i(Manager.FTAG,"HomeActivity: Starting service in homeactivity quit");
                    Intent intent = new Intent(HomeActivity.this,FahimWallpaperService.class);
                    startService(intent);
                }

                finish();
            }
        });
        button.setOnTouchListener(new FahimTouchEffect());

        button = (Button) quit_dialog.findViewById(R.id.noButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit_dialog.dismiss();
            }
        });
        button.setOnTouchListener(new FahimTouchEffect());

        quit_dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
