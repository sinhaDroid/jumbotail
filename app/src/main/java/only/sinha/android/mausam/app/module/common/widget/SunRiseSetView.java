package only.sinha.android.mausam.app.module.common.widget;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import only.sinha.android.mausam.app.R;

/**
 * Created by deepanshu on 2/6/17.
 */

public class SunRiseSetView extends View {
    int arcEndColor;
    int arcLineColor;
    Paint arcLinePaint;
    float arcLineWidth;
    Paint arcPaint;
    private float arcRadius;
    RectF arcRectF;
    int arcStartColor;
    int dashLineColor;
    float dashLineWidth;
    Paint dashPaint;
    RectF drawAreaRectF;
    BitmapDrawable sunIcon;
    float sunPercentage;
    ObjectAnimator sunPercentageAnimator;

    public SunRiseSetView(Context context) {
        super(context);
        this.sunPercentage = 0.0f;
        this.arcLinePaint = new Paint(1);
        this.arcPaint = new Paint(1);
        this.dashPaint = new Paint();
        this.drawAreaRectF = new RectF();
        this.arcRectF = new RectF();
        init();
    }

    public SunRiseSetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.sunPercentage = 0.0f;
        this.arcLinePaint = new Paint(1);
        this.arcPaint = new Paint(1);
        this.dashPaint = new Paint();
        this.drawAreaRectF = new RectF();
        this.arcRectF = new RectF();
        init();
    }

    public SunRiseSetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.sunPercentage = 0.0f;
        this.arcLinePaint = new Paint(1);
        this.arcPaint = new Paint(1);
        this.dashPaint = new Paint();
        this.drawAreaRectF = new RectF();
        this.arcRectF = new RectF();
        init();
    }

    @TargetApi(21)
    public SunRiseSetView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.sunPercentage = 0.0f;
        this.arcLinePaint = new Paint(1);
        this.arcPaint = new Paint(1);
        this.dashPaint = new Paint();
        this.drawAreaRectF = new RectF();
        this.arcRectF = new RectF();
        init();
    }

    void init() {
        this.arcLineColor = ContextCompat.getColor(getContext(), R.color.sun_rise_set_arc_line_color);
        this.arcStartColor = ContextCompat.getColor(getContext(), R.color.sun_rise_set_arc_start_color);
        this.arcEndColor = ContextCompat.getColor(getContext(), R.color.sun_rise_set_arc_end_color);
        this.dashLineColor = ContextCompat.getColor(getContext(), R.color.sun_rise_set_arc_dash_color);
        this.dashLineWidth = getResources().getDimension(R.dimen.dp_2);
        this.arcLineWidth = this.dashLineWidth;
        this.sunIcon = (BitmapDrawable) ContextCompat.getDrawable(getContext(), R.mipmap.icon_sun_rise_set_sun);
        setLayerType(1, null);
        this.arcPaint.setStyle(Paint.Style.FILL);
        this.arcLinePaint.setStyle(Paint.Style.STROKE);
        this.arcLinePaint.setColor(this.arcLineColor);
        this.arcLinePaint.setStrokeWidth(getResources().getDimension(R.dimen.dp_2));
        this.dashPaint.setStyle(Paint.Style.STROKE);
        this.dashPaint.setStrokeWidth(this.dashLineWidth);
        this.dashPaint.setColor(this.dashLineColor);
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        setMeasuredDimension(size, size / 2);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float width = ((float) getWidth()) / 2.0f;
        width = ((float) getHeight()) / 2.0f;
        this.sunIcon.setBounds(0, 0, this.sunIcon.getIntrinsicWidth(), this.sunIcon.getIntrinsicHeight());
        this.drawAreaRectF.set((float) getPaddingLeft(), ((float) getPaddingTop()) + (((float) this.sunIcon.getIntrinsicHeight()) / 2.0f), (float) (i - getPaddingRight()), (float) (i2 - getPaddingBottom()));
        if (this.drawAreaRectF.width() < this.drawAreaRectF.height() * 2.0f) {
            this.arcRadius = this.drawAreaRectF.width() / 2.0f;
        } else {
            this.arcRadius = this.drawAreaRectF.height();
        }
        this.arcRadius *= 0.8f;
        this.arcRadius -= this.arcLineWidth;
        width = this.arcRadius * 2.0f;
        this.arcRectF.set(0.0f, 0.0f, width, width);
        this.arcRectF.offset(this.drawAreaRectF.centerX() - this.arcRectF.centerX(), this.drawAreaRectF.bottom - this.arcRectF.centerY());
        this.arcPaint.setShader(new LinearGradient(this.arcRectF.centerX(), this.arcRectF.top, this.arcRectF.centerX(), this.arcRectF.bottom, this.arcStartColor, 0, Shader.TileMode.CLAMP));
        width = ((float) getWidth()) / 56.0f;
        this.arcLinePaint.setPathEffect(new DashPathEffect(new float[]{width, width * 0.5f, width, width * 0.5f}, 1.0f));
    }

    public void setSunPercentage(float f) {
        this.sunPercentage = f;
        invalidate();
    }

    public void playSunAnimation() {
        if (this.sunPercentageAnimator == null || !this.sunPercentageAnimator.isRunning()) {
            this.sunPercentageAnimator = ObjectAnimator.ofFloat(this, "sunPercentage", new float[]{0.0f, this.sunPercentage}).setDuration(3000);
            this.sunPercentageAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            this.sunPercentageAnimator.start();
        }
    }

    protected void onDetachedFromWindow() {
        if (this.sunPercentageAnimator == null || !this.sunPercentageAnimator.isRunning()) {
            super.onDetachedFromWindow();
        } else {
            this.sunPercentageAnimator.end();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.clipRect(this.drawAreaRectF);
        float max = Math.max(Math.min(this.sunPercentage, 1.0f), 0.0f);
        float width = this.arcRectF.left + (this.arcRectF.width() * max);
        canvas.save();
        canvas.clipRect(0.0f, 0.0f, width, this.arcRectF.bottom);
        canvas.drawArc(this.arcRectF, 180.0f, 180.0f, true, this.arcPaint);
        canvas.restore();
        canvas.drawArc(this.arcRectF, 0.0f, 360.0f, true, this.arcLinePaint);
        canvas.drawLine(this.drawAreaRectF.left, this.arcRectF.centerY() - (this.dashLineWidth / 2.0f), this.drawAreaRectF.right, this.arcRectF.centerY() - (this.dashLineWidth / 2.0f), this.dashPaint);
        canvas.restore();
        if (0.0f <= this.sunPercentage && this.sunPercentage <= 1.0f) {
            float centerY;
            if (max == 0.0f || max == 1.0f) {
                centerY = this.arcRectF.centerY();
            } else {
                double sqrt = Math.sqrt(Math.pow((double) this.arcRadius, 2.0d) - Math.pow((double) (width - this.arcRectF.centerX()), 2.0d));
                if (sqrt > 0.0d) {
                    centerY = (float) ((-sqrt) + ((double) this.arcRectF.centerY()));
                } else {
                    centerY = this.arcRectF.centerY();
                }
            }
            this.sunIcon.setBounds((int) (width - (((float) this.sunIcon.getIntrinsicWidth()) / 2.0f)), (int) (centerY - (((float) this.sunIcon.getIntrinsicHeight()) / 2.0f)), (int) ((((float) this.sunIcon.getIntrinsicWidth()) / 2.0f) + width), (int) (centerY + (((float) this.sunIcon.getIntrinsicHeight()) / 2.0f)));
            this.sunIcon.draw(canvas);
        }
    }
}
