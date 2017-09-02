package com.sinha.android.widgets.customfont;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.sinha.android.R;


public class CustomTextView extends AppCompatTextView {


    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            init(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        CustomFont.getInstance().setFont(context, this, a.getInt(R.styleable.CustomTextView_typeface, -1));
        a.recycle();
    }
}