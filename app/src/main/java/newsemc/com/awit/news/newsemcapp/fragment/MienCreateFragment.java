package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.GridSingleActivity;
import newsemc.com.awit.news.newsemcapp.adapter.MienAdapter;


/**
 * Created by Administrator on 2015/7/5.
 */
public class MienCreateFragment extends Fragment implements AdapterView.OnItemClickListener{
    private GridView listView;
    private List<Map<String,Object>> list_view;
    public static MienFragment newInstance(){
        MienFragment f = new MienFragment();
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.mienfragment, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.setClass(getActivity(), GridSingleActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("itemimg",list_view.get(position).get("drawer_list_itme_img")+"");
        bundle.putString("itemname",list_view.get(position).get("drawer_list_itme_name")+"");
        bundle.putString("itemdate",list_view.get(position).get("drawer_list_itme_date")+"");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
