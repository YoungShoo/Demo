package com.shoo.demo.widget.recyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoo.demo.R;
import com.shoo.demo.util.AnimUtils;

/**
 * Created by Shoo on 16-11-6.
 */

public class MyRecyclerView extends RecyclerView implements AnimUtils.AnimViewContainer {

    private View mFloatView;
    private Bitmap mFloatBitmap;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        if (mFloatView != null) {
//            Paint paint = new Paint();
//            paint.setColor(Color.RED);
//            paint.setStyle(Paint.Style.FILL);
//            paint.setStrokeWidth(10F);
//            canvas.drawRect(200, 200, 400, 400, paint);

//            canvas.translate(0, mFloatView.getTop());
            canvas.clipRect(0, 0, mFloatBitmap.getWidth(), mFloatBitmap.getHeight());
            canvas.drawColor(Color.BLACK);

            TextView view = new TextView(mFloatView.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
            view.setBackgroundColor(Color.RED);
            view.setBackgroundResource(R.drawable.ic_launcher);
            view.draw(canvas);

            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//            canvas.drawBitmap(mFloatBitmap, new Matrix(), new Paint());
            Matrix matrix = new Matrix();
            matrix.setScale(0.8f, 0.8f);
            canvas.scale(0.8f, 0.8f);
            canvas.drawBitmap(mFloatBitmap, 0, 0, null);
//            mFloatView.draw(canvas);
        }

        canvas.restore();
    }

    @Override
    public void performDeleteAnim(final View view) {
        mFloatView = createFloatView(view);
//        int location[] = new int[2];
//        view.getLocationOnScreen(location);
//        mFloatViewInfo = new FloatViewInfo(location);

        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        final int originHeight = layoutParams.height;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(view.getMeasuredHeight(), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.height = (int) animation.getAnimatedValue();
                view.requestLayout();

                mFloatView.setTop(view.getTop());
                mFloatView.setBottom(view.getBottom());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                layoutParams.height = originHeight;
                view.setVisibility(View.VISIBLE);
                mFloatView = null;
                mFloatBitmap = null;
            }
        });
        valueAnimator.start();
    }

    private View createFloatView(View view) {
        view.setDrawingCacheEnabled(true);
        mFloatBitmap = loadBitmapFromView(view);
        view.setDrawingCacheEnabled(false);

        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(mFloatBitmap);
        imageView.setLayoutParams(new LayoutParams(view.getWidth(), view.getHeight()));

        return imageView;
    }

    private Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        v.draw(c);
        return screenshot;
    }
}
