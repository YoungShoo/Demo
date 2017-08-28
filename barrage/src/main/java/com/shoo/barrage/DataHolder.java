package com.shoo.barrage;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Shoo on 17-8-22.
 */

public class DataHolder {

    private PriorityBlockingQueue<BarrageBean> mBarrageQueue;

    public DataHolder() {
        mBarrageQueue = new PriorityBlockingQueue<>();
    }

    public void addBarrage(BarrageBean bean) {
        mBarrageQueue.offer(bean);
    }

    public void removeBarrage(BarrageBean bean) {
        mBarrageQueue.remove(bean);
    }

    public Collection<BarrageBean> getBarrages() {
        return mBarrageQueue;
    }

    public void clear() {
        mBarrageQueue.clear();
    }

    public void reset() {
        for (BarrageBean barrage : mBarrageQueue) {
            barrage.reset();
        }
    }

    private boolean mIsMocking = false;

    public void mock() {
        if (mIsMocking) {
            return;
        }
        mIsMocking = true;

        final String str = "随机弹幕跟随页面滑动随机弹幕跟随页面滑动随机弹幕跟随页面滑动";

        for (int i = 0; i < 200; i++) {
            BarrageBean item = mockBarrageItem(str, 2);
            addBarrage(item);
        }
    }

    @NonNull
    private static BarrageBean mockBarrageItem(String str, int i) {
        BarrageBean bean;
        bean = new BarrageBean();
        double random = Math.random();
        int rgb = (int) (Math.random() * 255);

        int x = (int) (Math.random() * 1080);
        int y = (int) (Math.random() * 10000);
        int begin = new Random().nextInt(str.length());
        int end = begin + begin % 3;
        end = end >= str.length() ? str.length() : end;
        Log.d("Shoo", "mock: random = " + random + ", rgb = " + rgb + ", x = " + x + ", y = " + y + ", begin = " +
                begin + ", end = " + end);

        bean.setX(x);
        bean.setY(y);
        bean.setContent(str.substring(begin, end));
        bean.setTextColor(Color.rgb(rgb, 255 - rgb, 255 - rgb / 2));
        bean.setDuration(500);
        return bean;
    }
}
