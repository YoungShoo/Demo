package com.shoo.demo.share;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.Button;

/**
 * Created by Shoo on 17-1-5.
 */

public class TCustomShare implements CustomShareUtils.ActivityName {

    public static void test(final Activity activity) {
        Button btn = new Button(activity);
        btn.setText("点击分享");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomShareUtils.share(activity, CustomShareUtils.MIME_TYPE_TEXT,
                        new CustomShareUtils.CustomShareHandler() {
                    @Override
                    public void onCustomShare(Intent intent, ResolveInfo resolveInfo) {
                        // 可以通过resolveInfo判断具体应用，然后分别设置不同的参数
                        intent.putExtra(Intent.EXTRA_SUBJECT, "主题");
                        String activityName = resolveInfo.activityInfo.name;
                        switch (activityName) {
                            case MMS:
                                // 新信息
                                intent.putExtra(Intent.EXTRA_TEXT, "发消息给小胖子");
                                break;
                            case NOTE_PAPER:
                                // 便签
                                intent.putExtra(Intent.EXTRA_TITLE, "便签标题");
                                intent.putExtra(Intent.EXTRA_TEXT, "分享内容至便签");
                                break;
                            case WECHAT_TIMELINE:
                                // 微信朋友圈
                                intent.putExtra(Intent.EXTRA_TEXT, "分享内容至朋友圈");
                                break;
                            default:
                                // 其他
                                intent.putExtra(Intent.EXTRA_TEXT, "普通分享");
                                break;
                        }
                    }
                });
            }
        });

        activity.setContentView(btn);
    }

}
