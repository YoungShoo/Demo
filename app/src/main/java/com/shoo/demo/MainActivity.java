package com.shoo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shoo.demo.webview.TSchemeIntentUrl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        Tester.test(TPercentLayout.class, this);
//        Tester.test(TView.class, this);
//        Tester.test(TLinearLayout.class, this);
//        Tester.test(TScrollView.class, this);
//        Tester.test(TThreadLocal.class, this);
//        Tester.test(TAdapterViewFlipper.class, this);
//        TokenManager.test();
//        Tester.test(TRecyclerView.class, this);
//        TStackRecyclerView.test(this);
//        TCustomShare.test(this);
//        TSystemUiFlags.test(this);
//        TDrawablePadding.test(this);
//        TNinePatchDrawable.test(this);
//        TConcurrentModification.test();

        TSchemeIntentUrl.test(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        TokenManager.getInstance().shutDown();
    }
}
