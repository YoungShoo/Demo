package com.shoo.barrage;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Shoo on 17-8-25.
 */
class ActionHandler extends Handler {

    public static final int BUBBLING = 0x1;
    public static final int INVALIDATE = 0x2;

    private WeakReference<View> mViewRef;

    public ActionHandler(View view) {
        mViewRef = new WeakReference<>(view);
    }

    public void start() {
        removeMessages(BUBBLING);
        sendEmptyMessage(BUBBLING);
    }

    public void resume() {
        removeMessages(BUBBLING);
        sendEmptyMessage(BUBBLING);
    }

    public void pause() {
        removeMessages(BUBBLING);
    }

    public void stop() {
        removeMessages(BUBBLING);
        sendEmptyMessage(INVALIDATE);
    }

    @Override
    public void handleMessage(Message msg) {
        View view = mViewRef.get();
        if (view == null) {
            return;
        }

        switch (msg.what) {
            case BUBBLING:
                view.invalidate();
                sendEmptyMessage(BUBBLING);
                break;
            case INVALIDATE:
                view.invalidate();
                break;
            default:
                break;
        }
    }
}
