package com.shoo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.shoo.demo.rxjava.TRetryWhen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.btn_in_frame_layout);

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
//        TEnumeratedAnnotation.test();
//        TGridView.test(this);
//        TRetrofit2.test();
//        TInitialOrder.test();
//        TClassAndObjectLock.test();
//        TSuperAndExtends.test();
//        TVolley.test(this);
//        TPicasso.test(this);
//        TExceptionStack.test();
//        TOnSubscribeThread.test();
//        TBroadcast.test(this);
//        TSchemeIntentUrl.test(this);
//        TOverlapTouchEvent.test(this);
//        TTextBounds.test(this);
//        TBarrageView.test(this);
//        TRemoteBroadcast.test(this);
//        TBindService.test(this);
//        TSystemProperty.test(this);
//        TNotification.test(this);
//        TEditText.test(this);

        TRetryWhen.test();

//        TConcat.test();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // key back event should be listened by overriding EditText.onKeyPreIme if ime is open
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        TokenManager.getInstance().shutDown();
    }
}
