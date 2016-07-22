package com.bumpaw.bonuses.shopaholic;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * Created by user on 22/07/2016.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private OnItemSelectedCallback onItemSelectedCallback;
    private int position;
    private TextView tvSubtotal;

    public CustomOnItemSelectedListener(int position, TextView tvSubtotal, OnItemSelectedCallback onItemSelectedCallback){
        this.position = position;
        this.onItemSelectedCallback = onItemSelectedCallback;
        this.tvSubtotal = tvSubtotal;
    }

    public interface OnItemSelectedCallback{
        void onItemSelected(View view, TextView textView, int itemRowPosition, int itemArrayPosition);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        onItemSelectedCallback.onItemSelected(view, tvSubtotal, this.position, position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
