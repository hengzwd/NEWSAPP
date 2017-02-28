package newsemc.com.awit.news.newsemcapp.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by admin on 2016/2/26.
 */
public class AsyncImageLoader1 {private HashMap<String, SoftReference<Bitmap>> imageCache;

    public AsyncImageLoader1() {
        imageCache = new HashMap<String, SoftReference<Bitmap>>();
    }

    public Bitmap loadDrawable(final String imageUrl, final int scrWidth, final ImageCallback imageCallback) {
        if (imageCache.containsKey(imageUrl)) {
            SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
            Bitmap drawable = softReference.get();
            if (drawable != null) {
                return drawable;
            }
        }
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
            }
        };
        new Thread() {
            @Override
            public void run() {
                Bitmap drawable = loadImageFromUrl(imageUrl,scrWidth);
                imageCache.put(imageUrl, new SoftReference<Bitmap>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }

    public static Bitmap loadImageFromUrl(String url,int width) {
        URL m;
        InputStream i = null;
        try {
            m = new URL(url);
            i = (InputStream) m.getContent();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(i, "src");
        float x_scale = (float)width / (float)d.getIntrinsicWidth();
        //Log.d("缩放比例：",x_scale + "");
        return getRoundedCornerBitmap(zoomDrawable(d, x_scale, x_scale), 10);
    }

    /**
     * Drawable转化为Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * Bitmap to Drawable
     * @param bitmap
     * @param mcontext
     * @return
     */
    public static Drawable bitmapToDrawble(Bitmap bitmap,Context mcontext){
        Drawable drawable = new BitmapDrawable(mcontext.getResources(), bitmap);
        return drawable;
    }

    /**
     * 缩小图片
     * @param drawable
     * @return
     */
    public static Bitmap zoomDrawable(Drawable drawable, float x_scale, float y_scale) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
//        if(width < height)
//        {
//            int tmp = w;
//            w = h;
//            h = tmp;
//        }
//        // drawable转换成bitmap
        Bitmap oldbmp = drawableToBitmap(drawable);
//        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
//        // 计算缩放比例
//        float sx = ((float) w / width);
//        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.setScale(x_scale, y_scale);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return newbmp;
    }

    /**
     * Bitmap缩放
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if(height > width) return bitmap;
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 圆角图片
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public interface ImageCallback {
        public void imageLoaded(Bitmap imageDrawable, String imageUrl);
    }




}
