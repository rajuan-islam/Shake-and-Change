package com.fahim.shakenchange;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

public class Manager {

    ////////// SENSOR VALUES START //////////
    public static int SENSOR_THRESHOLD_VALUE = 1600;
    public static boolean IS_SENSOR_ACTIVE = true;
    public static boolean INSIDE_APP = false;
    public static long NEXT_TIME;
    public static long TIME_GAP = 2000;
    ////////// SENSOR VALUES END //////////

    ////////// COLORS START //////////
    // blue
    public static int FAHIM_BLUE = Color.argb(255,74,125,226);
    public static int FAHIM_BLUE_LIGHT = Color.argb(255,112,151,224);
    public static int FAHIM_BLUE_DARK = Color.argb(255,62,106,188);
    // grey
    public static int FAHIM_GREY = Color.argb(255,45,45,45);
    public static int FAHIM_GREY_TEXT = Color.argb(255,200,200,200);
    public static int FAHIM_GREY_TEXT_LIGHT = Color.argb(255,210,210,210);
    // others
    public static int WHITE = Color.argb(255,255,255,255);
    public static int TRANSPARENT_BLACK = Color.argb(125,0,0,0);
    public static int TRANSPARENT = Color.argb(0,0,0,0);
    public static int TRANSPARENT_PRESSED = Color.argb(60,0,0,0);
    public static int TRANSPARENT_BLACK_LIGHT = Color.argb(75,0,0,0);
    public static int TEST_RED = Color.argb(255,183,73,68);
    ////////// COLORS END //////////

    ////////// SERVICE HOLDER :P //////////

    ////////// CONTAINERS //////////
    public static ArrayList<Container> picList;
    public static int SIZE;

    ////////// TRACKERS //////////
    public static int currentPreview=0, currentSet=0;

    public static String FTAG = "com.fahim.shakenchange";

    static {
        Log.i(Manager.FTAG,"Static code block of manager");



        picList = new ArrayList<Container>();
        // adding default wallpapers
        picList.add( new Container(R.drawable.wallpaper_1,R.drawable.sample_1) );
        picList.add( new Container(R.drawable.wallpaper_2,R.drawable.sample_2) );
        picList.add( new Container(R.drawable.wallpaper_3,R.drawable.sample_3) );
        picList.add( new Container(R.drawable.wallpaper_4,R.drawable.sample_4) );
        picList.add( new Container(R.drawable.wallpaper_5,R.drawable.sample_5) );

        picList.add( new Container(R.drawable.wallpaper_6,R.drawable.sample_6) );
        picList.add( new Container(R.drawable.wallpaper_7,R.drawable.sample_7) );
        picList.add( new Container(R.drawable.wallpaper_8,R.drawable.sample_8) );
        picList.add( new Container(R.drawable.wallpaper_9,R.drawable.sample_9) );
        picList.add( new Container(R.drawable.wallpaper_10,R.drawable.sample_10) );
        // storing the total wallpaper number
        SIZE = picList.size();
    }

    public static void setNextWallpaper(Context context){
        loadPref(context);
        currentSet++;
        if(currentSet>=SIZE)currentSet=0;
        savePref(context);
    }

    public static void loadPref( Context context ){
        try {
            SharedPreferences fahimPref = context.getSharedPreferences("shakeNchange_info", Context.MODE_PRIVATE);
            IS_SENSOR_ACTIVE = fahimPref.getBoolean("isSensorActive",true);
            SENSOR_THRESHOLD_VALUE = fahimPref.getInt("sensorThresholdValue", 1600);
            currentSet = fahimPref.getInt("currentSet",0);

            //Toast.makeText(context,"successfully loaded",Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            //Toast.makeText(context,"loading failed!",Toast.LENGTH_SHORT).show();
        }
    }

    public static void savePref( Context context ){
        try{
            SharedPreferences fahimPref = context.getSharedPreferences("shakeNchange_info",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = fahimPref.edit();

            editor.putBoolean("isSensorActive", IS_SENSOR_ACTIVE);
            editor.putInt("sensorThresholdValue", SENSOR_THRESHOLD_VALUE);
            editor.putInt("currentSet", currentSet);
            editor.apply();

            //Toast.makeText(context,"successfully saved",Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            //Toast.makeText(context,"saving failed!",Toast.LENGTH_SHORT).show();
        }
    }

    public static void setSensorActive( boolean isActive, Context context ){
        IS_SENSOR_ACTIVE = isActive;
        savePref(context);
    }

    public static void setSensorThresholdValue( int sensor_value, Context context ){
        SENSOR_THRESHOLD_VALUE = sensor_value;
        savePref(context);
    }
}
