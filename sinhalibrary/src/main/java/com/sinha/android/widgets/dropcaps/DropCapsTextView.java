package com.sinha.android.widgets.dropcaps;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.sinha.android.R;
import com.sinha.android.widgets.customfont.CustomTextView;

/**
 * Created by Sinha on 17/7/16.
 */
public class DropCapsTextView extends CustomTextView {

    private int mDropCapTextColor;

    private int mDropCapTextSize;

    private int mDropCapPadding;

    public DropCapsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context, attrs);
    }

    public DropCapsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropCapsTextView);

        mDropCapTextColor = a.getColor(R.styleable.DropCapsTextView_dropCapTextColor, -1);

        mDropCapTextSize = a.getDimensionPixelSize(R.styleable.DropCapsTextView_dropCapTextSize, 14);

        mDropCapPadding = a.getDimensionPixelSize(R.styleable.DropCapsTextView_dropCapPadding, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String primaryText = getText().toString();
        String dropCapText = String.valueOf(primaryText.charAt(0));
        primaryText = primaryText.substring(1);

        // Get height of multiline text
        float textHeight = getLineHeight();

        // Drop cap paint
        TextPaint dropCapPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        dropCapPaint.setColor(mDropCapTextColor);
        dropCapPaint.setTextSize(mDropCapTextSize);

        Rect bounds = new Rect();
        dropCapPaint.getTextBounds(dropCapText, 0, 1, bounds);
        int dropCapWidth = bounds.width();
        int dropCapHeight = bounds.height();

        canvas.drawText(dropCapText, 0, dropCapHeight + mDropCapPadding + 15, dropCapPaint);

        // Actual text Paint
        TextPaint paint = getPaint();

        int secondLevelWidth = getResources().getDisplayMetrics().widthPixels - mDropCapPadding;

        int firstLevelWidth = secondLevelWidth - dropCapWidth - mDropCapPadding - getPaddingLeft() - getPaddingRight() - 10;
        float x = dropCapWidth + (mDropCapPadding) + getPaddingLeft();
        float y = textHeight;

        StaticLayout staticLayout = new StaticLayout(primaryText, paint, firstLevelWidth,
                Layout.Alignment.ALIGN_NORMAL, getLineSpacingMultiplier(), 0, false);

        int firstLevelLineCount = Math.round(dropCapHeight / textHeight);
        for (int i = 0; i < firstLevelLineCount; i++) {
            canvas.drawText(primaryText.substring(staticLayout.getLineStart(i), staticLayout.getLineEnd(i)),
                    x + 10, (i + 1) * y + getPaddingTop(), paint);
        }

        String secondLevelText = primaryText.substring(staticLayout.getLineEnd(firstLevelLineCount - 1));
        staticLayout = new StaticLayout(secondLevelText, paint, secondLevelWidth,
                Layout.Alignment.ALIGN_NORMAL, getLineSpacingMultiplier(), 0, false);
        x = x - dropCapWidth - mDropCapPadding;
        for (int i = 0; i < staticLayout.getLineCount(); i++) {
            canvas.drawText(secondLevelText.substring(staticLayout.getLineStart(i), staticLayout.getLineEnd(i)),
                    x, (i + 1 + firstLevelLineCount) * y + getPaddingTop(), paint);
        }

    }
}