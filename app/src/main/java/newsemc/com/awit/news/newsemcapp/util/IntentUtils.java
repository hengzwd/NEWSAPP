package newsemc.com.awit.news.newsemcapp.util;

import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.net.URI;

/**
 * Created by lianghl on 2015/12/9.
 * 获取一个调用系统应用打开文件的intent意图
 * 参数： 要打开的目标文件
 */
public class IntentUtils {

    //android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent( String param )
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent( String param )
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent( String param )
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent( String param )
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    //android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent( String param, boolean paramBoolean)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean)
        {
            Uri uri1 = Uri.parse(param );
            intent.setDataAndType(uri1, "text/plain");
        }
        else
        {
            Uri uri2 = Uri.fromFile(new File(param ));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    //android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent( String param )
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }
//Android获取一个用于打开图片的intent
    public static Intent getImageFileIntent( String param )
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }
    //Android获取一个用于打开html的intent
    public static  Intent getHtmlIntent(String param){
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }
    //Android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(String param){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "audio/*");
        return intent;

    }

    //Android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(String param){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }
    //Android获取一个用于打开apk文件的intent
    public static  Intent getApkIntent(String param){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(param), "application/vnd.android.package-archive");
        return intent;

    }

    //Android获取一个用于打开rar文件的intent
    public static  Intent getRarFileIntent(String param){
        Intent intent = new Intent();
        intent.setClassName("com.rarlab.rar", "com.rarlab.rar.NewsDetailActivty");
        File file = new File(param);
        Uri data = Uri.parse(param);
        intent.setData(data);
        return intent;
    }
}
