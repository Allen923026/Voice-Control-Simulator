package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class RealTimeGraphView extends View {
    private Paint vPaint, aPaint, axisPaint, textPaint;
    private List<Float> vHistory = new ArrayList<>();
    private List<Float> aHistory = new ArrayList<>();
    private final int MAX_POINTS = 50; // 畫面上顯示的點數

    public RealTimeGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        vPaint = new Paint(); vPaint.setColor(Color.YELLOW); vPaint.setStrokeWidth(5); vPaint.setStyle(Paint.Style.STROKE);
        aPaint = new Paint(); aPaint.setColor(Color.CYAN); aPaint.setStrokeWidth(5); aPaint.setStyle(Paint.Style.STROKE);
        axisPaint = new Paint(); axisPaint.setColor(Color.WHITE); axisPaint.setStrokeWidth(3);
        textPaint = new Paint(); textPaint.setColor(Color.WHITE); textPaint.setTextSize(30);
    }

    public void addData(float v, float a) {
        vHistory.add(v);
        aHistory.add(a);
        if (vHistory.size() > MAX_POINTS) {
            vHistory.remove(0);
            aHistory.remove(0);
        }
        invalidate(); // 請求重繪
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = getWidth();
        float h = getHeight();
        float margin = 40;

        // 畫座標軸
        canvas.drawLine(margin, margin, margin, h - margin, axisPaint); // Y
        canvas.drawLine(margin, h - margin, w - margin, h - margin, axisPaint); // X
        canvas.drawText("V(黃) & a(藍)", margin, margin - 10, textPaint);

        if (vHistory.size() < 2) return;

        float stepX = (w - 2 * margin) / MAX_POINTS;
        for (int i = 1; i < vHistory.size(); i++) {
            float x1 = margin + (i - 1) * stepX;
            float x2 = margin + i * stepX;

            // 畫速度 V (高度比例縮放，假設最大速度 500)
            float yV1 = (h - margin) - (vHistory.get(i - 1) / 500f) * (h - 2 * margin);
            float yV2 = (h - margin) - (vHistory.get(i) / 500f) * (h - 2 * margin);
            canvas.drawLine(x1, Math.max(margin, yV1), x2, Math.max(margin, yV2), vPaint);

            // 畫加速度 a (假設最大加速度 50)
            float yA1 = (h - margin) - (aHistory.get(i - 1) / 50f) * (h - 2 * margin);
            float yA2 = (h - margin) - (aHistory.get(i) / 50f) * (h - 2 * margin);
            canvas.drawLine(x1, Math.max(margin, yA1), x2, Math.max(margin, yA2), aPaint);
        }
    }
}