package com.huimee.advertlibrary;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by YX on 2019/12/14 10:40.
 */
public class AdvertImageView extends AppCompatImageView {
    public AdvertImageView(Context context) {
        super(context);
    }

    public AdvertImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvertImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public  void setImage(Activity activity,  String s) {
        DialogUtils.INSTANCE.showDialog(activity,s,3,-1,this);
    }
}
