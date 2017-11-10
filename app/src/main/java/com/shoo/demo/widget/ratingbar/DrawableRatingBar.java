package com.shoo.demo.widget.ratingbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * Created by Shoo on 17-11-9.
 */

public class DrawableRatingBar extends AppCompatRatingBar {

    public DrawableRatingBar(Context context) {
        super(context);
    }

    public DrawableRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable instanceof LayerDrawable) {
            Drawable drawable = ((LayerDrawable) progressDrawable).getDrawable(DrawableIndex.PROGRESS);
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            setMeasuredDimension(resolveSizeAndState(width * getNumStars(), widthMeasureSpec, 0),
                    resolveSizeAndState(height, widthMeasureSpec, 0));
        }
    }

    public void setProgressDrawable(Bitmap bitmap) {
        setDrawable(DrawableIndex.PROGRESS, bitmap);
    }

    public void setSecondaryDrawable(Bitmap bitmap) {
        setDrawable(DrawableIndex.SECONDARY, bitmap);
    }

    public void setBackgroundDrawable(Bitmap bitmap) {
        setDrawable(DrawableIndex.BACKGROUND, bitmap);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void setDrawable(int index, Bitmap bitmap) {
        Drawable drawable = createDrawable(index, bitmap);
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable instanceof LayerDrawable) {
            ((LayerDrawable) progressDrawable).setDrawable(index, drawable);
        }
    }

    private static Drawable createDrawable(int index, Bitmap bitmap) {
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.getPaint().setShader(bitmapShader);
        shapeDrawable.setIntrinsicWidth(bitmap.getWidth());
        shapeDrawable.setIntrinsicHeight(bitmap.getHeight());

        if (DrawableIndex.BACKGROUND == index) {
            return shapeDrawable;
        }
        return new ClipDrawable(shapeDrawable, Gravity.START, ClipDrawable.HORIZONTAL);
    }
}
