package com.shoo.barrage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Shoo on 17-8-22.
 */

public class BarrageDrawer {

    private Paint mPaint;
    private long mLastTime;

    public BarrageDrawer() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(40);
        mLastTime = -1;
    }

    public void draw(Canvas canvas, Collection<BarrageBean> list) {
        ensureLastTime();
        Iterator<BarrageBean> iterator = list.iterator();
        Rect clipBounds = canvas.getClipBounds();
        Log.d(Constants.LOG_TAG, "draw: clipBounds = " + clipBounds);
        RectF bound = new RectF();
        long start = SystemClock.uptimeMillis();
        int count = 0;
        Random random = new Random();
        while (iterator.hasNext()) {
            BarrageBean item = iterator.next();

            String content = item.getContent();
            if (TextUtils.isEmpty(content)) {
                continue;
            }

            if (!item.resolveBounds(clipBounds) || !item.resolveState()) {
                Log.d(Constants.LOG_TAG, "draw: invalid barrage item " + item);
                continue;
            }

            if (BarrageState.READY == item.getState() && random.nextInt(300) != 100) {
                continue;
            }

            item.setupNextAlpha(SystemClock.elapsedRealtime() - mLastTime);
            item.setupNextState();

            Log.d(Constants.LOG_TAG, "draw: start drawing bubble " + item);
            int x = item.getX();
            int y = item.getY();
            int alpha = item.getDrawingAlpha();

            Rect rect = new Rect();
            mPaint.getTextBounds(content, 0, content.length(), rect);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            mPaint.setColor(item.getTextColor());
            mPaint.setAlpha(alpha);
            int spacing = 15;
            bound.set(x - spacing, y + fontMetrics.ascent - spacing / 2,
                    x + rect.width() + spacing, y + fontMetrics.descent + spacing / 2);
            canvas.drawRoundRect(bound, spacing, spacing, mPaint);

            mPaint.setColor(Color.BLACK);
            mPaint.setAlpha(alpha);
            canvas.drawText(content, x, y, mPaint);

            count++;
        }

        mLastTime = SystemClock.elapsedRealtime();
        Log.d(Constants.LOG_TAG, "draw: takes " + (SystemClock.uptimeMillis() - start) + " millis, count = " +
                count);
    }

    private void ensureLastTime() {
        if (mLastTime <= 0) {
            mLastTime = SystemClock.elapsedRealtime();
        }
    }
}
