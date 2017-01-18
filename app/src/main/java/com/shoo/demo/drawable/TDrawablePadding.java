package com.shoo.demo.drawable;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoo.demo.R;

/**
 * Created by xiaoyuanxiu on 17-1-18.
 */
public class TDrawablePadding {

    public static void test(Activity activity) {
        TextView textView = new TextView(activity);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1000));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setText("Test Drawable Padding");

        testDrawablePadding(activity, textView);
        testImageSpan(activity, textView);

        activity.setContentView(textView);
    }

    private static void testDrawablePadding(Activity activity, TextView textView) {
        /**
         * 注意：
         * 通过setBackgroundResource(resId)重新设置同一资源，不会做任何操作，Padding不会有改动；
         * 而通过setBackground(Drawable)重新设置同一资源，Padding会重新设置；
         */
        // 1. 显示9图padding
        textView.setBackgroundResource(R.drawable.wg_bg);
        // 2. 显示主动设置的padding
        textView.setPadding(0, 0, 0, 0);
        // 3. 背景资源相同，不会做任何操作
        textView.setBackgroundResource(R.drawable.wg_bg);

        // 4. 以Drawable而非ResId方式重新设置背景，显示Drawable的padding
        textView.setBackground(activity.getResources().getDrawable(R.drawable.wg_bg));
        // 5. 显示主动设置的padding
        textView.setPadding(0, 0, 0, 0);
        // 6. 以Drawable而非ResId方式重新设置背景，显示Drawable的padding
        textView.setBackground(activity.getResources().getDrawable(R.drawable.wg_bg));
    }

    private static void testImageSpan(Activity activity, TextView textView) {
        // 占位文本
        String placeHolder = "placeHolder ";
        // 图标与文本间距：这里用空格进行处理
        String spacing = " ";
        // 实际显示内容
        String content = "text after picture";
        SpannableString spannableString = new SpannableString(placeHolder + spacing + content);
        Drawable drawable = activity.getResources().getDrawable(R.drawable.ic_launcher);
        // 必须主要设置Bounds，否则无法显示
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        // 多参构造函数，默认底部对齐
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, 0, placeHolder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        // 设置富文本
        textView.setText(spannableString);
    }
}
