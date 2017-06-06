package only.sinha.android.mausam.app.module.common.widget;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import only.sinha.android.mausam.app.R;

/**
 * Created by deepanshu on 2/6/17.
 */

public class HighLowTempView extends View {
    RectF areaClipRectF;
    Paint areaPaint;
    Path areaPath;
    Rect chartClip;
    ObjectAnimator chartObjectAnimator;
    float chartPercentage;
    float drawTranslateX;
    Shader gradientDown2Up;
    Shader gradientUp2Down;
    int highLineColor;
    Paint highLinePaint;
    Path highLinePath;
    ArrayList highPointBounds;
    Drawable highPointDrawable;
    String[] highPointTexts;
    float[] highPoints;
    ArrayList highTempBounds;
    TextPaint highTempPaint;
    float itemWidth;
    float lineWidth;
    int lowLineColor;
    Paint lowLinePaint;
    Path lowLinePath;
    ArrayList lowPointBounds;
    Drawable lowPointDrawable;
    String[] lowPointTexts;
    float[] lowPoints;
    ArrayList lowTempBounds;
    TextPaint lowTempPaint;

    public HighLowTempView(Context context) {
        this(context, null);
    }

    public HighLowTempView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HighLowTempView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.areaPaint = new Paint(1);
        this.highLinePath = new Path();
        this.lowLinePath = new Path();
        this.areaPath = new Path();
        this.highPointBounds = new ArrayList();
        this.highTempBounds = new ArrayList();
        this.lowPointBounds = new ArrayList();
        this.lowTempBounds = new ArrayList();
        this.chartObjectAnimator = ObjectAnimator.ofFloat(this, "chartPercentage", new float[]{0.0f, 1.0f});
        this.chartPercentage = 1.0f;
        this.chartClip = new Rect();
        this.areaClipRectF = new RectF();
        init(context, attributeSet);
    }

    @TargetApi(21)
    public HighLowTempView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.areaPaint = new Paint(1);
        this.highLinePath = new Path();
        this.lowLinePath = new Path();
        this.areaPath = new Path();
        this.highPointBounds = new ArrayList();
        this.highTempBounds = new ArrayList();
        this.lowPointBounds = new ArrayList();
        this.lowTempBounds = new ArrayList();
        this.chartObjectAnimator = ObjectAnimator.ofFloat(this, "chartPercentage", 0.0f, 1.0f);
        this.chartPercentage = 1.0f;
        this.chartClip = new Rect();
        this.areaClipRectF = new RectF();
        init(context, attributeSet);
    }

    void init(Context context, AttributeSet attributeSet) {
        setLayerType(1, null);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LineChartView);
        this.itemWidth = obtainStyledAttributes.getDimension(R.styleable.LineChartView_chart_item_width, getResources().getDimension(R.dimen.dp_45));
        this.lineWidth = obtainStyledAttributes.getDimension(R.styleable.LineChartView_chart_line_width, getResources().getDimension(R.dimen.dp_1_5));
        obtainStyledAttributes.recycle();
        this.highPointDrawable = ContextCompat.getDrawable(getContext(), R.drawable.line_chart_point_high);
        this.highLineColor = ContextCompat.getColor(getContext(), R.color.line_chart_high_line);
        this.lowPointDrawable = ContextCompat.getDrawable(getContext(), R.drawable.line_chart_point_low);
        this.lowLineColor = ContextCompat.getColor(getContext(), R.color.line_chart_low_line);
        this.highLinePaint = new Paint(1);
        this.highLinePaint.setColor(this.highLineColor);
        this.highLinePaint.setStrokeWidth(this.lineWidth);
        this.highLinePaint.setStyle(Paint.Style.STROKE);
        this.lowLinePaint = new Paint(1);
        this.lowLinePaint.setColor(this.lowLineColor);
        this.lowLinePaint.setStrokeWidth(this.lineWidth);
        this.lowLinePaint.setStyle(Paint.Style.STROKE);
        float cornerPathRadius = getResources().getDimension(R.dimen.dp_14);
        PathEffect cornerPathEffect = new CornerPathEffect(cornerPathRadius);
        this.highLinePaint.setPathEffect(cornerPathEffect);
        this.lowLinePaint.setPathEffect(cornerPathEffect);
        this.areaPaint.setPathEffect(cornerPathEffect);
        this.highTempPaint = new TextPaint(1);
        this.highTempPaint.setColor(this.highLineColor);
        this.highTempPaint.setTextAlign(Paint.Align.CENTER);
        this.highTempPaint.setTextSize(context.getResources().getDimension(R.dimen.sp_14));
        this.lowTempPaint = new TextPaint(1);
        this.lowTempPaint.setColor(this.lowLineColor);
        this.lowTempPaint.setTextAlign(Paint.Align.CENTER);
        this.lowTempPaint.setTextSize(context.getResources().getDimension(R.dimen.sp_14));
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.highPoints != null) {
            updateChart();
            invalidate();
        }
    }

    boolean isPointsOk(float[] fArr) {
        return fArr != null && fArr.length > 0;
    }

    boolean isBoundsOk(List list) {
        return list != null && list.size() > 0;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.chartPercentage != 0.0f && isPointsOk(this.highPoints) && isPointsOk(this.lowPoints) && isBoundsOk(this.highPointBounds) && isBoundsOk(this.lowPointBounds)) {
            canvas.save();
            this.chartClip.set(0, 0, (int) (((float) getWidth()) * this.chartPercentage), getHeight());
            canvas.clipRect(this.chartClip);
            canvas.translate(this.drawTranslateX, 0.0f);
            canvas.save();
            canvas.clipRect(this.areaClipRectF);
            this.areaPaint.setShader(this.gradientUp2Down);
            canvas.drawPath(this.areaPath, this.areaPaint);
            this.areaPaint.setShader(this.gradientDown2Up);
            canvas.drawPath(this.areaPath, this.areaPaint);
            canvas.restore();
            canvas.drawPath(this.highLinePath, this.highLinePaint);
            canvas.drawPath(this.lowLinePath, this.lowLinePaint);
            for (int i = 0; i < this.highPoints.length; i++) {
                RectF rectF = (RectF) this.highPointBounds.get(i);
                this.highPointDrawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                this.highPointDrawable.draw(canvas);
                rectF = (RectF) this.highTempBounds.get(i);
                canvas.drawText(this.highPointTexts[i], rectF.centerX(), rectF.centerY(), this.highTempPaint);
                rectF = (RectF) this.lowPointBounds.get(i);
                this.lowPointDrawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                this.lowPointDrawable.draw(canvas);
                rectF = (RectF) this.lowTempBounds.get(i);
                canvas.drawText(this.lowPointTexts[i], rectF.centerX(), rectF.centerY(), this.lowTempPaint);
            }
            canvas.restore();
        }
    }

    protected void onDetachedFromWindow() {
        if (this.chartObjectAnimator.isRunning()) {
            this.chartObjectAnimator.end();
        }
        super.onDetachedFromWindow();
    }

    public void setChartPercentage(float f) {
        this.chartPercentage = f;
        invalidate();
    }

    public void playChartAnimation() {
        if (!this.chartObjectAnimator.isRunning()) {
            this.chartObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            this.chartObjectAnimator.setDuration(3000);
            this.chartObjectAnimator.start();
        }
    }

    public void setDrawTranslateX(float f) {
        this.drawTranslateX = f;
        invalidate();
    }

    public void translateDrawXBy(float f) {
        setDrawTranslateX(this.drawTranslateX + f);
    }

    public static float baseLineTranslateY(TextPaint textPaint) {
        return ((textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent) / 2.0f) - textPaint.getFontMetrics().descent;
    }

    public static float computeCenterDrawY(float f, float f2, float f3) {
        return ((f + f2) / 2.0f) + f3;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void updateChart() {
        /*
        r34 = this;
        r0 = r34;
        r4 = r0.highPoints;
        if (r4 == 0) goto L_0x0018;
    L_0x0006:
        r0 = r34;
        r4 = r0.lowPoints;
        if (r4 == 0) goto L_0x0018;
    L_0x000c:
        r0 = r34;
        r4 = r0.highPointTexts;
        if (r4 == 0) goto L_0x0018;
    L_0x0012:
        r0 = r34;
        r4 = r0.lowPointTexts;
        if (r4 != 0) goto L_0x0019;
    L_0x0018:
        return;
    L_0x0019:
        r0 = r34;
        r4 = r0.highPoints;
        r4 = r4.length;
        r0 = r34;
        r5 = r0.lowPoints;
        r5 = r5.length;
        if (r4 != r5) goto L_0x0018;
    L_0x0025:
        r0 = r34;
        r4 = r0.highPoints;
        r4 = r4.length;
        r0 = r34;
        r5 = r0.highPointTexts;
        r5 = r5.length;
        if (r4 != r5) goto L_0x0018;
    L_0x0031:
        r0 = r34;
        r4 = r0.highPoints;
        r4 = r4.length;
        r0 = r34;
        r5 = r0.lowPointTexts;
        r5 = r5.length;
        if (r4 != r5) goto L_0x0018;
    L_0x003d:
        r0 = r34;
        r4 = r0.highPoints;
        r4 = r4.clone();
        r4 = (float[]) r4;
        java.util.Arrays.sort(r4);
        r5 = 0;
        r7 = r4[r5];
        r5 = r4.length;
        r5 = r5 + -1;
        r8 = r4[r5];
        r0 = r34;
        r4 = r0.lowPoints;
        r4 = r4.clone();
        r4 = (float[]) r4;
        java.util.Arrays.sort(r4);
        r5 = 0;
        r9 = r4[r5];
        r5 = r4.length;
        r5 = r5 + -1;
        r10 = r4[r5];
        r12 = new android.graphics.RectF;
        r4 = r34.getPaddingLeft();
        r4 = (float) r4;
        r5 = r34.getPaddingTop();
        r5 = (float) r5;
        r6 = r34.getWidth();
        r11 = r34.getPaddingRight();
        r6 = r6 - r11;
        r6 = (float) r6;
        r11 = r34.getHeight();
        r13 = r34.getPaddingBottom();
        r11 = r11 - r13;
        r11 = (float) r11;
        r12.<init>(r4, r5, r6, r11);
        r0 = r34;
        r4 = r0.highPointDrawable;
        r4 = r4.getIntrinsicWidth();
        r0 = r34;
        r5 = r0.lowPointDrawable;
        r5 = r5.getIntrinsicWidth();
        r4 = java.lang.Math.max(r4, r5);
        r4 = (float) r4;
        r0 = r34;
        r5 = r0.highPointDrawable;
        r5 = r5.getIntrinsicHeight();
        r0 = r34;
        r6 = r0.lowPointDrawable;
        r6 = r6.getIntrinsicHeight();
        r5 = java.lang.Math.max(r5, r6);
        r5 = (float) r5;
        r6 = java.lang.Math.max(r4, r5);
        r11 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r11 = r6 / r11;
        r0 = r34;
        r6 = r0.highTempPaint;
        r6 = r6.getFontMetrics();
        r13 = r6.bottom;
        r6 = r6.top;
        r6 = r13 - r6;
        r13 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r13 = r6 / r13;
        r14 = 1067869798; // 0x3fa66666 float:1.3 double:5.275977814E-315;
        r14 = r14 * r6;
        r0 = r34;
        r6 = r0.highTempPaint;
        r15 = baseLineTranslateY(r6);
        r6 = new android.graphics.RectF;
        r16 = 0;
        r17 = 0;
        r18 = r12.width();
        r4 = r18 - r4;
        r18 = r12.height();
        r5 = r18 - r5;
        r0 = r16;
        r1 = r17;
        r6.<init>(r0, r1, r4, r5);
        r4 = r12.centerX();
        r5 = r6.centerX();
        r4 = r4 - r5;
        r5 = r12.centerY();
        r16 = r6.centerY();
        r5 = r5 - r16;
        r6.offset(r4, r5);
        r4 = r6.top;
        r4 = r4 + r14;
        r4 = r4 + r13;
        r5 = r6.bottom;
        r16 = r14 + r13;
        r5 = r5 - r16;
        r16 = r5 - r4;
        r17 = r8 - r9;
        r16 = r16 / r17;
        r17 = r8 - r7;
        r17 = r17 * r16;
        r17 = r17 + r4;
        r18 = r10 - r9;
        r16 = r16 * r18;
        r16 = r5 - r16;
        r18 = new android.graphics.RectF;
        r0 = r6.left;
        r19 = r0;
        r0 = r6.right;
        r20 = r0;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r3 = r17;
        r0.<init>(r1, r4, r2, r3);
        r17 = new android.graphics.RectF;
        r4 = r6.left;
        r6 = r6.right;
        r0 = r17;
        r1 = r16;
        r0.<init>(r4, r1, r6, r5);
        r0 = r34;
        r4 = r0.highLinePath;
        r4.rewind();
        r0 = r34;
        r4 = r0.lowLinePath;
        r4.rewind();
        r0 = r34;
        r4 = r0.areaPath;
        r4.rewind();
        r0 = r34;
        r4 = r0.highPoints;
        r0 = r4.length;
        r16 = r0;
        r0 = r16;
        r0 = new float[r0];
        r19 = r0;
        r0 = r16;
        r0 = new float[r0];
        r20 = r0;
        r0 = r16;
        r0 = new float[r0];
        r21 = r0;
        r0 = r16;
        r0 = new float[r0];
        r22 = r0;
        r0 = r34;
        r4 = r0.highPointBounds;
        r4.clear();
        r0 = r34;
        r4 = r0.highTempBounds;
        r4.clear();
        r0 = r34;
        r4 = r0.lowPointBounds;
        r4.clear();
        r0 = r34;
        r4 = r0.lowTempBounds;
        r4.clear();
        r0 = r34;
        r4 = r0.cornerPathRadius;
        r5 = 1036831949; // 0x3dcccccd float:0.1 double:5.122630465E-315;
        r23 = r4 * r5;
        r4 = 0;
        r6 = r4;
    L_0x01a2:
        r0 = r16;
        if (r6 >= r0) goto L_0x03d8;
    L_0x01a6:
        r0 = r34;
        r4 = r0.itemWidth;
        r5 = (float) r6;
        r4 = r4 * r5;
        r0 = r34;
        r5 = r0.itemWidth;
        r24 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r5 = r5 / r24;
        r24 = r4 + r5;
        r0 = r34;
        r4 = r0.highPoints;
        r25 = r4[r6];
        r0 = r18;
        r4 = r0.bottom;
        r5 = r25 - r7;
        r26 = r8 - r7;
        r5 = r5 / r26;
        r26 = r18.height();
        r5 = r5 * r26;
        r26 = r4 - r5;
        r0 = r34;
        r4 = r0.lowPoints;
        r27 = r4[r6];
        r0 = r17;
        r4 = r0.bottom;
        r5 = r27 - r9;
        r28 = r10 - r9;
        r5 = r5 / r28;
        r28 = r17.height();
        r5 = r5 * r28;
        r28 = r4 - r5;
        r4 = 0;
        r5 = 0;
        if (r6 <= 0) goto L_0x055c;
    L_0x01ea:
        r29 = r16 + -1;
        r0 = r29;
        if (r6 >= r0) goto L_0x055c;
    L_0x01f0:
        r0 = r34;
        r0 = r0.highPoints;
        r29 = r0;
        r30 = r6 + -1;
        r29 = r29[r30];
        r29 = r25 - r29;
        r0 = r34;
        r0 = r0.highPoints;
        r30 = r0;
        r31 = r6 + 1;
        r30 = r30[r31];
        r25 = r25 - r30;
        r30 = 0;
        r30 = (r29 > r30 ? 1 : (r29 == r30 ? 0 : -1));
        if (r30 <= 0) goto L_0x0212;
    L_0x020e:
        r30 = (r29 > r25 ? 1 : (r29 == r25 ? 0 : -1));
        if (r30 > 0) goto L_0x021c;
    L_0x0212:
        r30 = 0;
        r30 = (r25 > r30 ? 1 : (r25 == r30 ? 0 : -1));
        if (r30 <= 0) goto L_0x0378;
    L_0x0218:
        r30 = (r25 > r29 ? 1 : (r25 == r29 ? 0 : -1));
        if (r30 <= 0) goto L_0x0378;
    L_0x021c:
        r4 = r4 + r23;
    L_0x021e:
        r30 = 0;
        r30 = (r29 > r30 ? 1 : (r29 == r30 ? 0 : -1));
        if (r30 >= 0) goto L_0x022d;
    L_0x0224:
        r0 = r25;
        r0 = -r0;
        r30 = r0;
        r30 = (r29 > r30 ? 1 : (r29 == r30 ? 0 : -1));
        if (r30 < 0) goto L_0x023c;
    L_0x022d:
        r30 = 0;
        r30 = (r25 > r30 ? 1 : (r25 == r30 ? 0 : -1));
        if (r30 >= 0) goto L_0x0388;
    L_0x0233:
        r0 = r29;
        r0 = -r0;
        r30 = r0;
        r30 = (r25 > r30 ? 1 : (r25 == r30 ? 0 : -1));
        if (r30 >= 0) goto L_0x0388;
    L_0x023c:
        r4 = r4 - r23;
    L_0x023e:
        r0 = r34;
        r0 = r0.lowPoints;
        r25 = r0;
        r29 = r6 + -1;
        r25 = r25[r29];
        r25 = r27 - r25;
        r0 = r34;
        r0 = r0.lowPoints;
        r29 = r0;
        r30 = r6 + 1;
        r29 = r29[r30];
        r27 = r27 - r29;
        r29 = 0;
        r29 = (r25 > r29 ? 1 : (r25 == r29 ? 0 : -1));
        if (r29 <= 0) goto L_0x0260;
    L_0x025c:
        r29 = (r25 > r27 ? 1 : (r25 == r27 ? 0 : -1));
        if (r29 > 0) goto L_0x026a;
    L_0x0260:
        r29 = 0;
        r29 = (r27 > r29 ? 1 : (r27 == r29 ? 0 : -1));
        if (r29 <= 0) goto L_0x0398;
    L_0x0266:
        r29 = (r27 > r25 ? 1 : (r27 == r25 ? 0 : -1));
        if (r29 <= 0) goto L_0x0398;
    L_0x026a:
        r5 = r5 + r23;
    L_0x026c:
        r29 = 0;
        r29 = (r25 > r29 ? 1 : (r25 == r29 ? 0 : -1));
        if (r29 >= 0) goto L_0x027b;
    L_0x0272:
        r0 = r27;
        r0 = -r0;
        r29 = r0;
        r29 = (r25 > r29 ? 1 : (r25 == r29 ? 0 : -1));
        if (r29 < 0) goto L_0x028a;
    L_0x027b:
        r29 = 0;
        r29 = (r27 > r29 ? 1 : (r27 == r29 ? 0 : -1));
        if (r29 >= 0) goto L_0x03a8;
    L_0x0281:
        r0 = r25;
        r0 = -r0;
        r29 = r0;
        r29 = (r27 > r29 ? 1 : (r27 == r29 ? 0 : -1));
        if (r29 >= 0) goto L_0x03a8;
    L_0x028a:
        r5 = r5 - r23;
        r33 = r5;
        r5 = r4;
        r4 = r33;
    L_0x0291:
        r0 = r34;
        r0 = r0.highPointBounds;
        r25 = r0;
        r27 = new android.graphics.RectF;
        r29 = r24 - r11;
        r30 = r26 - r11;
        r30 = r30 + r5;
        r31 = r24 + r11;
        r32 = r26 + r11;
        r5 = r5 + r32;
        r0 = r27;
        r1 = r29;
        r2 = r30;
        r3 = r31;
        r0.<init>(r1, r2, r3, r5);
        r0 = r25;
        r1 = r27;
        r0.add(r1);
        r5 = r26 + r15;
        r5 = r5 - r14;
        r0 = r34;
        r0 = r0.highTempPaint;
        r25 = r0;
        r0 = r34;
        r0 = r0.highPointTexts;
        r27 = r0;
        r27 = r27[r6];
        r0 = r25;
        r1 = r27;
        r25 = r0.measureText(r1);
        r27 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r25 = r25 / r27;
        r0 = r34;
        r0 = r0.highTempBounds;
        r27 = r0;
        r29 = new android.graphics.RectF;
        r30 = r24 - r25;
        r31 = r5 - r13;
        r25 = r25 + r24;
        r5 = r5 + r13;
        r0 = r29;
        r1 = r30;
        r2 = r31;
        r3 = r25;
        r0.<init>(r1, r2, r3, r5);
        r0 = r27;
        r1 = r29;
        r0.add(r1);
        r0 = r34;
        r5 = r0.lowPointBounds;
        r25 = new android.graphics.RectF;
        r27 = r24 - r11;
        r29 = r28 - r11;
        r29 = r29 + r4;
        r30 = r24 + r11;
        r31 = r28 + r11;
        r4 = r4 + r31;
        r0 = r25;
        r1 = r27;
        r2 = r29;
        r3 = r30;
        r0.<init>(r1, r2, r3, r4);
        r0 = r25;
        r5.add(r0);
        r4 = r28 + r15;
        r4 = r4 + r14;
        r0 = r34;
        r5 = r0.lowTempPaint;
        r0 = r34;
        r0 = r0.lowPointTexts;
        r25 = r0;
        r25 = r25[r6];
        r0 = r25;
        r5 = r5.measureText(r0);
        r25 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r5 = r5 / r25;
        r0 = r34;
        r0 = r0.lowTempBounds;
        r25 = r0;
        r27 = new android.graphics.RectF;
        r29 = r24 - r5;
        r30 = r4 - r13;
        r5 = r5 + r24;
        r4 = r4 + r13;
        r0 = r27;
        r1 = r29;
        r2 = r30;
        r0.<init>(r1, r2, r5, r4);
        r0 = r25;
        r1 = r27;
        r0.add(r1);
        r19[r6] = r24;
        r21[r6] = r24;
        r20[r6] = r26;
        r22[r6] = r28;
        if (r6 != 0) goto L_0x03bd;
    L_0x0359:
        r0 = r34;
        r4 = r0.highLinePath;
        r5 = r19[r6];
        r24 = r20[r6];
        r0 = r24;
        r4.moveTo(r5, r0);
        r0 = r34;
        r4 = r0.lowLinePath;
        r5 = r21[r6];
        r24 = r22[r6];
        r0 = r24;
        r4.moveTo(r5, r0);
    L_0x0373:
        r4 = r6 + 1;
        r6 = r4;
        goto L_0x01a2;
    L_0x0378:
        r30 = 0;
        r30 = (r29 > r30 ? 1 : (r29 == r30 ? 0 : -1));
        if (r30 <= 0) goto L_0x021e;
    L_0x037e:
        r30 = 0;
        r30 = (r25 > r30 ? 1 : (r25 == r30 ? 0 : -1));
        if (r30 <= 0) goto L_0x021e;
    L_0x0384:
        r4 = r4 + r23;
        goto L_0x021e;
    L_0x0388:
        r30 = 0;
        r29 = (r29 > r30 ? 1 : (r29 == r30 ? 0 : -1));
        if (r29 >= 0) goto L_0x023e;
    L_0x038e:
        r29 = 0;
        r25 = (r25 > r29 ? 1 : (r25 == r29 ? 0 : -1));
        if (r25 >= 0) goto L_0x023e;
    L_0x0394:
        r4 = r4 - r23;
        goto L_0x023e;
    L_0x0398:
        r29 = 0;
        r29 = (r25 > r29 ? 1 : (r25 == r29 ? 0 : -1));
        if (r29 <= 0) goto L_0x026c;
    L_0x039e:
        r29 = 0;
        r29 = (r27 > r29 ? 1 : (r27 == r29 ? 0 : -1));
        if (r29 <= 0) goto L_0x026c;
    L_0x03a4:
        r5 = r5 + r23;
        goto L_0x026c;
    L_0x03a8:
        r29 = 0;
        r25 = (r25 > r29 ? 1 : (r25 == r29 ? 0 : -1));
        if (r25 >= 0) goto L_0x055c;
    L_0x03ae:
        r25 = 0;
        r25 = (r27 > r25 ? 1 : (r27 == r25 ? 0 : -1));
        if (r25 >= 0) goto L_0x055c;
    L_0x03b4:
        r5 = r5 - r23;
        r33 = r5;
        r5 = r4;
        r4 = r33;
        goto L_0x0291;
    L_0x03bd:
        r0 = r34;
        r4 = r0.highLinePath;
        r5 = r19[r6];
        r24 = r20[r6];
        r0 = r24;
        r4.lineTo(r5, r0);
        r0 = r34;
        r4 = r0.lowLinePath;
        r5 = r21[r6];
        r24 = r22[r6];
        r0 = r24;
        r4.lineTo(r5, r0);
        goto L_0x0373;
    L_0x03d8:
        r0 = r34;
        r4 = r0.areaClipRectF;
        r5 = 0;
        r5 = r19[r5];
        r6 = r12.top;
        r7 = r16 + -1;
        r7 = r19[r7];
        r8 = r12.bottom;
        r4.set(r5, r6, r7, r8);
        r4 = 0;
    L_0x03eb:
        r0 = r16;
        if (r4 >= r0) goto L_0x0443;
    L_0x03ef:
        if (r4 != 0) goto L_0x040e;
    L_0x03f1:
        r5 = 0;
        r5 = r19[r5];
        r6 = 0;
        r6 = r19[r6];
        r7 = 1;
        r7 = r19[r7];
        r6 = r6 - r7;
        r5 = r5 + r6;
        r6 = 0;
        r6 = r20[r6];
        r7 = 0;
        r7 = r20[r7];
        r8 = 1;
        r8 = r20[r8];
        r7 = r7 - r8;
        r6 = r6 + r7;
        r0 = r34;
        r7 = r0.areaPath;
        r7.moveTo(r5, r6);
    L_0x040e:
        r0 = r34;
        r5 = r0.areaPath;
        r6 = r19[r4];
        r7 = r20[r4];
        r5.lineTo(r6, r7);
        r5 = r16 + -1;
        if (r4 != r5) goto L_0x0440;
    L_0x041d:
        r5 = r16 + -1;
        r5 = r19[r5];
        r6 = r16 + -1;
        r6 = r19[r6];
        r7 = r16 + -2;
        r7 = r19[r7];
        r6 = r6 - r7;
        r5 = r5 + r6;
        r6 = r16 + -1;
        r6 = r20[r6];
        r7 = r16 + -1;
        r7 = r20[r7];
        r8 = r16 + -2;
        r8 = r20[r8];
        r7 = r7 - r8;
        r6 = r6 + r7;
        r0 = r34;
        r7 = r0.areaPath;
        r7.lineTo(r5, r6);
    L_0x0440:
        r4 = r4 + 1;
        goto L_0x03eb;
    L_0x0443:
        r4 = r16 + -1;
    L_0x0445:
        if (r4 < 0) goto L_0x049b;
    L_0x0447:
        r5 = r16 + -1;
        if (r4 != r5) goto L_0x046e;
    L_0x044b:
        r5 = r16 + -1;
        r5 = r21[r5];
        r6 = r16 + -1;
        r6 = r21[r6];
        r7 = r16 + -2;
        r7 = r21[r7];
        r6 = r6 - r7;
        r5 = r5 + r6;
        r6 = r16 + -1;
        r6 = r22[r6];
        r7 = r16 + -1;
        r7 = r22[r7];
        r8 = r16 + -2;
        r8 = r22[r8];
        r7 = r7 - r8;
        r6 = r6 + r7;
        r0 = r34;
        r7 = r0.areaPath;
        r7.lineTo(r5, r6);
    L_0x046e:
        r0 = r34;
        r5 = r0.areaPath;
        r6 = r21[r4];
        r7 = r22[r4];
        r5.lineTo(r6, r7);
        if (r4 != 0) goto L_0x0498;
    L_0x047b:
        r5 = 0;
        r5 = r21[r5];
        r6 = 0;
        r6 = r21[r6];
        r7 = 1;
        r7 = r21[r7];
        r6 = r6 - r7;
        r5 = r5 + r6;
        r6 = 0;
        r6 = r22[r6];
        r7 = 0;
        r7 = r22[r7];
        r8 = 1;
        r8 = r22[r8];
        r7 = r7 - r8;
        r6 = r6 + r7;
        r0 = r34;
        r7 = r0.areaPath;
        r7.lineTo(r5, r6);
    L_0x0498:
        r4 = r4 + -1;
        goto L_0x0445;
    L_0x049b:
        r0 = r34;
        r4 = r0.areaPath;
        r4.close();
        r4 = "#ffda48";
        r4 = android.graphics.Color.parseColor(r4);
        r5 = "#48b8ff";
        r13 = android.graphics.Color.parseColor(r5);
        r14 = 1053609165; // 0x3ecccccd float:0.4 double:5.205520926E-315;
        r15 = 0;
        r5 = 1132396544; // 0x437f0000 float:255.0 double:5.5947823E-315;
        r5 = r5 * r14;
        r5 = (int) r5;
        r6 = android.graphics.Color.red(r4);
        r7 = android.graphics.Color.green(r4);
        r8 = android.graphics.Color.blue(r4);
        r10 = android.graphics.Color.argb(r5, r6, r7, r8);
        r5 = 1132396544; // 0x437f0000 float:255.0 double:5.5947823E-315;
        r5 = r5 * r15;
        r5 = (int) r5;
        r6 = android.graphics.Color.red(r4);
        r7 = android.graphics.Color.green(r4);
        r4 = android.graphics.Color.blue(r4);
        r11 = android.graphics.Color.argb(r5, r6, r7, r4);
        r4 = new android.graphics.LinearGradient;
        r5 = r12.centerX();
        r0 = r18;
        r6 = r0.top;
        r7 = r12.centerX();
        r0 = r17;
        r8 = r0.bottom;
        r9 = 2;
        r9 = new int[r9];
        r16 = 0;
        r9[r16] = r10;
        r10 = 1;
        r9[r10] = r11;
        r10 = 2;
        r10 = new float[r10];
        r10 = {0, 1065353216};
        r11 = android.graphics.Shader.TileMode.CLAMP;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r34;
        r0.gradientUp2Down = r4;
        r4 = 1132396544; // 0x437f0000 float:255.0 double:5.5947823E-315;
        r4 = r4 * r15;
        r4 = (int) r4;
        r5 = android.graphics.Color.red(r13);
        r6 = android.graphics.Color.green(r13);
        r7 = android.graphics.Color.blue(r13);
        r10 = android.graphics.Color.argb(r4, r5, r6, r7);
        r4 = 1132396544; // 0x437f0000 float:255.0 double:5.5947823E-315;
        r4 = r4 * r14;
        r4 = (int) r4;
        r5 = android.graphics.Color.red(r13);
        r6 = android.graphics.Color.green(r13);
        r7 = android.graphics.Color.blue(r13);
        r11 = android.graphics.Color.argb(r4, r5, r6, r7);
        r4 = new android.graphics.LinearGradient;
        r5 = r12.centerX();
        r0 = r18;
        r6 = r0.top;
        r7 = r12.centerX();
        r0 = r17;
        r8 = r0.bottom;
        r9 = 2;
        r9 = new int[r9];
        r12 = 0;
        r9[r12] = r10;
        r10 = 1;
        r9[r10] = r11;
        r10 = 2;
        r10 = new float[r10];
        r10 = {0, 1065353216};
        r11 = android.graphics.Shader.TileMode.CLAMP;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r34;
        r0.gradientDown2Up = r4;
        r34.invalidate();
        goto L_0x0018;
    L_0x055c:
        r33 = r5;
        r5 = r4;
        r4 = r33;
        goto L_0x0291;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wea.climate.clock.widget.widget.HighLowTempView.updateChart():void");
    }

    public void setHighLowInfo(float[] fArr, float[] fArr2, String[] strArr, String[] strArr2) {
        this.highPoints = fArr;
        this.lowPoints = fArr2;
        this.highPointTexts = strArr;
        this.lowPointTexts = strArr2;
        if (getWidth() > 0) {
            updateChart();
        }
    }
}
