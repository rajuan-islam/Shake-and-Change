package com.fahim.shakenchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/*
* CUSTOM ADAPTER FOR LIST VIEW
* */
public class FahimAdapter extends ArrayAdapter<Container> {

    ArrayList<Container> pics;

    public FahimAdapter(Context context, ArrayList<Container> picList ) {
        super(context, R.layout.fahim_adapter_row , picList);
        pics = picList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // housekeeping
        LayoutInflater fahimInflater = LayoutInflater.from(getContext());
        // don't know anything about what happens here
        View fahimView = fahimInflater.inflate(R.layout.fahim_adapter_row,parent,false);

        // setting up everything;
        ImageView sampleImage = (ImageView) fahimView.findViewById(R.id.sampleImage);
        Container item = pics.get(position);
        sampleImage.setImageResource(item.sampleId);

        return fahimView;
    }
}
