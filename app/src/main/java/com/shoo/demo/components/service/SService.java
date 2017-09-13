package com.shoo.demo.components.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Shoo on 17-9-13.
 */

public class SService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }
}
