package com.fahim.shakenchange;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class FahimWallpaperService extends Service implements SensorEventListener {
    public FahimWallpaperService() {
        Log.i(Manager.FTAG,"Service Constructor");
    }

    public boolean FIRST_CALL=true;
    SensorManager sensorManager;
    Sensor motion;
    long lastUpdate;
    double lastX, lastY, lastZ;

    boolean isWorking=false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Manager.FTAG,"Service: On create method");

        // loading static data
        Manager.loadPref(this);

        // registering sensor event listeners
        if( Manager.IS_SENSOR_ACTIVE ){
            isWorking=true;
            FIRST_CALL=true;
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            if( sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size()!=0 ){
                motion = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
                sensorManager.registerListener(this,motion,SensorManager.SENSOR_DELAY_NORMAL);
            }
            Manager.NEXT_TIME = System.currentTimeMillis() + Manager.TIME_GAP;
        } else isWorking=false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(FIRST_CALL){
            FIRST_CALL=false;
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

            if( Manager.IS_SENSOR_ACTIVE && speed>Manager.SENSOR_THRESHOLD_VALUE && currentTime>Manager.NEXT_TIME ){ // checking against threshold value
                Manager.NEXT_TIME = System.currentTimeMillis() + Manager.TIME_GAP;
                shake();
            }

            lastUpdate = currentTime;               // keeping track
            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    public void shake(){
        Manager.setNextWallpaper(this);
        Container item = Manager.picList.get(Manager.currentSet);
        try {
            Bitmap wallpic = BitmapFactory.decodeStream(getResources().openRawResource(item.imageId));
            getApplicationContext().setWallpaper(wallpic);
            //Toast.makeText(FahimWallpaperService.this,"Wallpaper Changed",Toast.LENGTH_SHORT).show();
            sendNotification();
        } catch (Exception e){
            Toast.makeText(FahimWallpaperService.this,"Wallpaper could not be changed due to an error!",Toast.LENGTH_SHORT).show();
        }
    }

    public void sendNotification(){
        NotificationCompat.Builder fahimNotification = new NotificationCompat.Builder(this);
        fahimNotification.setAutoCancel(false);

        Container item = Manager.picList.get(Manager.currentSet);
        fahimNotification.setSmallIcon(item.sampleId);

        fahimNotification.setTicker("Wallpaper Changed!");
        fahimNotification.setWhen(System.currentTimeMillis());
        fahimNotification.setContentTitle("Wallpaper Changed.");
        fahimNotification.setContentText("Successfully changed wallpaper by shaking.");



        NotificationManager nfm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nfm.notify( 6258+Manager.currentSet, fahimNotification.build() );
    }

    public static void inspectManagerClass(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(Manager.FTAG, "Service: On destroy method");
        if( isWorking ){
            try{
                sensorManager.unregisterListener(this,motion);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(Manager.FTAG,"Service: On start command");
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
