package only.sinha.android.mausam.app.module.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import only.sinha.android.mausam.app.R;

/**
 * Created by deepanshu on 2/6/17.
 */

public class LineChartView extends View {
    Paint areaPaint;
    Path areaPath;
    float drawTranslateX;
    float itemWidth;
    int lineColor;
    Paint linePaint;
    Path linePath;
    float lineWidth;
    List<RectF> pointBounds;
    Drawable pointDrawable;
    float pointSize;
    float[] points;
    int visibleCount;

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LineChartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.areaPaint = new Paint();
        this.visibleCount = 8;
        this.linePath = new Path();
        this.areaPath = new Path();
        this.pointBounds = new ArrayList();
        init(context, attributeSet);
    }

    @TargetApi(21)
    public LineChartView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.areaPaint = new Paint();
        this.visibleCount = 8;
        this.linePath = new Path();
        this.areaPath = new Path();
        this.pointBounds = new ArrayList();
        init(context, attributeSet);
    }

    void init(Context context, AttributeSet attributeSet) {
        setLayerType(1, null);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LineChartView);
        this.itemWidth = obtainStyledAttributes.getDimension(R.styleable.LineChartView_chart_item_width, getResources().getDimension(R.dimen.dp_60));
        this.pointDrawable = obtainStyledAttributes.getDrawable(R.styleable.LineChartView_chart_point_drawable);
        if (this.pointDrawable == null) {
            this.pointDrawable = ContextCompat.getDrawable(getContext(), R.drawable.line_chart_point);
        }
        this.lineColor = obtainStyledAttributes.getColor(R.styleable.LineChartView_chart_line_color, -1);
        this.lineWidth = obtainStyledAttributes.getDimension(R.styleable.LineChartView_chart_line_width, getResources().getDimension(R.dimen.dp_1_3));
        obtainStyledAttributes.recycle();
        this.linePaint = new Paint(1);
        this.linePaint.setColor(this.lineColor);
        this.linePaint.setStrokeWidth(this.lineWidth);
        this.linePaint.setStyle(Paint.Style.STROKE);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.points != null) {
            updateChart((float) i, (float) i2);
            invalidate();
        }
        int argb = Color.argb(0, Color.red(this.lineColor), Color.green(this.lineColor), Color.blue(this.lineColor));
        this.areaPaint.setShader(new LinearGradient((float) (i / 2), 0.0f, (float) (i / 2), (float) getHeight(), Color.argb(191, Color.red(this.lineColor), Color.green(this.lineColor), Color.blue(this.lineColor)), argb, Shader.TileMode.CLAMP));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.points != null) {
            canvas.save();
            canvas.translate(this.drawTranslateX, 0.0f);
            canvas.drawPath(this.areaPath, this.areaPaint);
            canvas.drawPath(this.linePath, this.linePaint);
            if (this.pointDrawable != null) {
                for (int i = 0; i < this.points.length; i++) {
                    RectF rectF = (RectF) this.pointBounds.get(i);
                    this.pointDrawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                    this.pointDrawable.draw(canvas);
                }
            }
            canvas.restore();
        }
    }

    public void setDrawTranslateX(float f) {
        this.drawTranslateX = f;
        invalidate();
    }

    public void translateDrawXBy(float f) {
        setDrawTranslateX(this.drawTranslateX + f);
    }

    void updateChart(float f, float f2) {
        float paddingLeft = ((float) getPaddingLeft()) + (((float) this.pointDrawable.getMinimumWidth()) / 2.0f);
        float paddingTop = ((float) getPaddingTop()) + (((float) this.pointDrawable.getMinimumHeight()) / 2.0f);
        float paddingRight = (f - ((float) getPaddingRight())) - (((float) this.pointDrawable.getMinimumWidth()) / 2.0f);
        float paddingBottom = (f2 - ((float) getPaddingBottom())) - (((float) this.pointDrawable.getMinimumHeight()) / 2.0f);
        float[] fArr = (float[]) this.points.clone();
        Arrays.sort(fArr);
        float f3 = fArr[0];
        float f4 = fArr[fArr.length - 1];
        float f5 = paddingBottom - paddingTop;
        this.linePath.rewind();
        this.areaPath.rewind();
        float f6 = 0.0f;
        float max = ((float) Math.max(this.pointDrawable.getMinimumWidth(), this.pointDrawable.getMinimumHeight())) / 2.0f;
        for (int i = 0; i < this.points.length; i++) {
            float f7 = paddingBottom - (((this.points[i] - f3) / (f4 - f3)) * f5);
            float f8 = (this.itemWidth * ((float) i)) + (this.itemWidth / 2.0f);
            this.pointBounds.add(new RectF(f8 - max, f7 - max, f8 + max, f7 + max));
            if (i == 0) {
                this.linePath.moveTo(f8, f7);
                this.areaPath.moveTo(f8, f7);
                paddingTop = f7;
                paddingLeft = f8;
            } else {
                this.linePath.lineTo(f8, f7);
                this.areaPath.lineTo(f8, f7);
                if (i == this.points.length - 1) {
                    f6 = f8;
                }
            }
        }
        this.areaPath.lineTo(f6, (float) getHeight());
        this.areaPath.lineTo(paddingLeft, (float) getHeight());
        this.areaPath.lineTo(paddingLeft, paddingTop);
    }

    public void setPointDrawable(Drawable drawable) {
        this.pointDrawable = drawable;
    }

    public void setLineColor(int i) {
        this.lineColor = i;
        invalidate();
    }

    public void setPoints(float[] fArr) {
        this.points = fArr;
        if (getWidth() > 0) {
            updateChart((float) getWidth(), (float) getHeight());
        }
    }
}
