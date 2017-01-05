package com.shoo.demo.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoo on 17-1-5.
 */

public class CustomShareUtils {

    private static final String TAG = "CustomShareUtils";

    /**
     * 应用包名
     */
    public interface PackageName {
        // 新浪微博
        String SINA_WEIBO = "com.sina.weibo";
        // 新浪微博Pro
        String SINA_WEIBO_PRO = "com.sina.weibopro";
        // 微信
        String WECHAT = "com.tencent.mm";
        // QQ
        String QQ = "com.tencent.mobileqq";
    }

    /**
     * Activity名称
     */
    public interface ActivityName {
        // 微信好友
        String WECHAT_SESSION = "com.tencent.mm.ui.tools.ShareImgUI";
        // 微信朋友圈
        String WECHAT_TIMELINE = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
        // 微信收藏
        String WECHAT_FAVORITE = "com.tencent.mm.ui.tools.AddFavoriteUI";
        // 短信息
        String MMS = "com.android.mms.ui.ComposeMessageActivity";
        // 便签
        String NOTE_PAPER = "com.meizu.flyme.notepaper.app.NoteEditActivity";
    }

    /**
     * 数据类型
     */
    public static final String MIME_TYPE_TEXT = "text/plain";

    /**
     * 自定义分享
     *
     * @param activity 触发分享的Activity
     * @param mimeType 分享数据类型
     * @param handler 处理自定义分享
     */
    public static void share(Activity activity, String mimeType, @NonNull CustomShareHandler handler) {
        // 通过指定的分享类型，匹配所有目标分享应用／页面的信息
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(mimeType);
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(shareIntent, 0);

        // 遍历并配置参数
        if (resolveInfos != null && !resolveInfos.isEmpty()) {
            List<Intent> targetIntents = new ArrayList<>();
            for (ResolveInfo resolveInfo : resolveInfos) {
                /** 可以先打印Log，提取应用的信息，并保存至{@link PackageName}或者{@link ActivityName}接口中 */
                Log.d(TAG, "resovleInfo: " + resolveInfo);
                // 提取应用信息
                String srcPackageName = resolveInfo.activityInfo.packageName;   // 应用包名
                String srcActivityName = resolveInfo.activityInfo.name;         // 页面名称
                CharSequence label = resolveInfo.loadLabel(pm);                 // 分享标签
                int icon = resolveInfo.activityInfo.icon;                       // 分享图标

                // 如果目标Activity未配置图标，则使用目标应用图标
                if (icon == 0 && resolveInfo.activityInfo.applicationInfo != null) {
                    icon = resolveInfo.activityInfo.applicationInfo.icon;
                }
                // 如果无法正确获取分享图标，则不进行分享展示，否则将会显示本应用图标
                if (icon == 0 || pm.getDrawable(srcPackageName, icon, null) == null) {
                    continue;
                }

                // 根据各个应用的信息，创建对应Intent
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setComponent(new ComponentName(srcPackageName, srcActivityName));
                intent.setType(mimeType);
                // 在回调中设置各应用需要配置的参数
                handler.onCustomShare(intent, resolveInfo);
                // 保存所有Intent，作为后续分享时传入的自定义应用
                targetIntents.add(new LabeledIntent(intent, srcPackageName, label, icon));
            }

            /** 这里先以一个具体的应用创建分享，然后通过Intent.EXTRA_INITIAL_INTENTS传入剩余的分享项 */
            // 基于其中一个分享项创建分享：一般取最后一项，因为下面添加的自定义分享项会显示在最前面
            Intent chooserIntent = Intent.createChooser(targetIntents.remove(0), "分享标题");
            // 添加其他自定义分享项：这些分享项会显示在最前面
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Parcelable[]{}));
            activity.startActivity(chooserIntent);
        }
    }

    public interface CustomShareHandler {

        /**
         * 自定义应用分享参数的回调
         *
         * @param intent 包含应用包名及目标分享Activity名称
         * @param resolveInfo 目标分享Activity详细信息
         */
        void onCustomShare(Intent intent, ResolveInfo resolveInfo);
    }

}
