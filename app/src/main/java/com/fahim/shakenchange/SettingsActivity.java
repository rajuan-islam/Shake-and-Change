package com.fahim.shakenchange;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


public class SettingsActivity extends ActionBarActivity implements SensorEventListener {

    Switch sensorSwitch;
    TextView valueShow, switchShow;
    SeekBar sensorSeekBar;

    // sensor test materials
    TextView testColor;
    boolean FIRST_CALL = true;
    long lastUpdate;
    double lastX, lastY, lastZ;
    SensorManager sm;
    Sensor motion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // action bar modifications
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // switch setup
        sensorSwitch = (Switch) findViewById(R.id.sensorSwitch);
        sensorSwitch.setChecked(Manager.IS_SENSOR_ACTIVE);
        switchShow = (TextView) findViewById(R.id.switchShow);
        if( Manager.IS_SENSOR_ACTIVE ){
            switchShow.setText("Activated");
        } else {
            switchShow.setText("Deactivated");
        }
        sensorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Manager.setSensorActive(isChecked,SettingsActivity.this);
                if (Manager.IS_SENSOR_ACTIVE) {
                    switchShow.setText("Activated");
                } else {
                    switchShow.setText("Deactivated");
                }
            }
        });

        // seekbar setup
        valueShow = (TextView) findViewById(R.id.valueShow);
        valueShow.setText(Integer.toString(Manager.SENSOR_THRESHOLD_VALUE));
        sensorSeekBar = (SeekBar) findViewById(R.id.sensorSeekBar);
        sensorSeekBar.setProgress(Manager.SENSOR_THRESHOLD_VALUE / 800 - 1);
        sensorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Manager.setSensorThresholdValue( 800*(progress+1) , SettingsActivity.this );
                valueShow.setText( Integer.toString( Manager.SENSOR_THRESHOLD_VALUE ) );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // sensor setup
        testColor = (TextView) findViewById(R.id.testColor);
        testColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testColor.setBackgroundColor(Manager.FAHIM_BLUE);
            }
        });
        FIRST_CALL = true;
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if( sm.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0 ){
            motion = sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sm.registerListener(this,motion,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void shake(){
        testColor.setBackgroundColor(Manager.TEST_RED);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(FIRST_CALL){
            FIRST_CALL = false;
            lastUpdate = System.currentTimeMillis();
            lastX = event.values[0];
            lastY = event.values[1];
            lastZ = event.values[2];
            return;
        }

        long currentTime = System.currentTimeMillis();
        if( currentTime-lastUpdate>100 ){
            long DELTA = currentTime - lastUpdate;  // getting delta time

            double x = event.values[0];             // getting values
            double y = event.values[1];
            double z = event.values[2];

            double speed = Math.abs(x+y+z-lastX-lastY-lastZ)/DELTA*10000;   // gettings speed

            if( Manager.IS_SENSOR_ACTIVE && speed>Manager.SENSOR_THRESHOLD_VALUE ){ // checking against threshold value
                shake();
            }

            lastUpdate = currentTime;               // keeping track
            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(this, motion);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
