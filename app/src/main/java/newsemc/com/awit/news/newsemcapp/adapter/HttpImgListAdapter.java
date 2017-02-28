package newsemc.com.awit.news.newsemcapp.adapter;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.http.AsyncImageLoader;

/**
 * Created by Administrator on 2016/1/18.
 */
public class HttpImgListAdapter extends BaseAdapter  {
    private AsyncImageLoader asyncImageLoader;
    List<String> data;
    Context context;
    ImageView iv;
    private ListView listView;
    private int scrWidth = 0;

    public int getHeight() {
        return height;
    }

    private int height = 0;

    public HttpImgListAdapter(Context context, List<String> list,ListView listView,int scrWidth) {
        this.context = context;
        this.data = list;
        asyncImageLoader = new AsyncImageLoader();
        this.listView=listView;
        this.scrWidth = scrWidth;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.img_list,
                    null);
        }
        iv = (ImageView) convertView.findViewById(R.id.imageView1);

        String url=data.get(position).toString();
        iv.setTag(url);

        Bitmap cachedImage = asyncImageLoader.loadDrawable(url,scrWidth, new AsyncImageLoader.ImageCallback() {
            public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageBitmap(imageDrawable);
                    Log.d("imgHeight:",imageDrawable.getHeight() + "");
//                    ViewGroup.LayoutParams params = imageViewByTag.getLayoutParams();
//                    int oldWidth = params.width;
//                    params.width = scrWidth;
                   // params.height = imageViewByTag.getHeight() * (scrWidth / oldWidth);
                    //imageViewByTag.setLayoutParams(params);
                }
            }
        });
        if (cachedImage == null) {
            iv.setImageResource(R.drawable.loadingprogress_1);
        }else{
            iv.setImageBitmap(cachedImage);
            ViewGroup.LayoutParams para = iv.getLayoutParams();
            para.height = cachedImage.getHeight();
            iv.setLayoutParams(para);
            //height += cachedImage.getHeight();
        }
        return convertView;
    }
}
