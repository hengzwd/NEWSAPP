package newsemc.com.awit.news.newsemcapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import newsemc.com.awit.news.newsemcapp.R;

/**
 * Created by cll on 2015/10/13.
 */
public class ZYJHFragment extends Fragment {
    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=(View)inflater.inflate(R.layout.jspubliclistshow,null);
//        textView=(TextView)view.findViewById(R.id.no);
//        textView.setText("重要讲话");
        return view;
    }
}
