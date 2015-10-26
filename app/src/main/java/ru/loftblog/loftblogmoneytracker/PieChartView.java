package ru.loftblog.loftblogmoneytracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class PieChartView extends View{

    private Paint slicePaint;
    private RectF rectF;
    private float[] datapoints;

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        slicePaint = new Paint();
        slicePaint.setAntiAlias(true);
        slicePaint.setDither(true);
        slicePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(datapoints != null) {
            int startTop = 0;
            int startLeft = 0;
            int endBottom = getWidth();
            int endRight = endBottom;
            Random r = new Random();
            Paint innerPaintObj = new Paint();
            innerPaintObj.setColor(getResources().getColor(R.color.backGroundDark));
            innerPaintObj.setStyle(Paint.Style.FILL);
            innerPaintObj.setAntiAlias(true);
            rectF = new RectF(startLeft, startTop, endRight, endBottom);
            float[] scaledValues = scale();
            float sliceStartPoint = 0;
            for (float scaledValue : scaledValues) {
                int color = Color.argb(1200, r.nextInt(256), r.nextInt(256), r.nextInt(256));
                slicePaint.setColor(color);
                slicePaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("here is some text", startLeft, 400, innerPaintObj);
                canvas.drawArc(rectF, sliceStartPoint, scaledValue, true, slicePaint);
                sliceStartPoint += scaledValue;
                canvas.drawCircle(getWidth()/2, getHeight()/2, 190, innerPaintObj);

            }
        }
    }

    private float[] scale() {
        float[] scaledValues = new float[this.datapoints.length];
        float total = getTotal();
        for (int i = 0; i < this.datapoints.length; i++) {
            scaledValues[i] = (this.datapoints[i] / total) * 360;
        }
        return scaledValues;
    }

    public void setDatapoints (float[] datapoints) {
        this.datapoints = datapoints;
    }

    private float getTotal() {
        float total = 0;
        for (float val : this.datapoints)
            total += val;
        return total;
    }
}