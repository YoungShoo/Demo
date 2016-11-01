package com.shoo.demo.adapterviewflipper;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.Button;

import com.shoo.demo.R;
import com.shoo.demo.adapter.TextAdapter;
import com.shoo.demo.tester.Tester;

/**
 * Created by Shoo on 16-10-24.
 */
public class TAdapterViewFlipper extends Tester implements View.OnClickListener {

    private AdapterViewFlipper mFlipperNext;
    private AdapterViewFlipper mFlipperPrev;
    private Button mBtnNext;
    private Button mBtnPrev;

    public TAdapterViewFlipper(Activity activity) {
        super(activity);
    }

    @Override
    protected void test() {
        mActivity.setContentView(R.layout.adapter_view_flipper);
        mFlipperNext = (AdapterViewFlipper) mActivity.findViewById(R.id.flipper_next);
        mFlipperPrev = (AdapterViewFlipper) mActivity.findViewById(R.id.flipper_prev);

        mBtnNext = (Button) mActivity.findViewById(R.id.btn_next);
        mBtnPrev = (Button) mActivity.findViewById(R.id.btn_prev);

        mFlipperNext.setAdapter(new TextAdapter());
        mFlipperPrev.setAdapter(new TextAdapter());

        mBtnNext.setOnClickListener(this);
        mBtnPrev.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_next:
                mFlipperNext.setVisibility(View.VISIBLE);
                mFlipperPrev.setVisibility(View.GONE);
                mFlipperNext.showNext();
                mFlipperPrev.showNext();
                break;
            case R.id.btn_prev:
                mFlipperNext.setVisibility(View.GONE);
                mFlipperPrev.setVisibility(View.VISIBLE);
                mFlipperNext.showPrevious();
                mFlipperPrev.showPrevious();
                break;
            default:
                break;
        }
    }
}
