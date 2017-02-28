package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ImgInfoListImpl;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by cll on 2015/10/13.
 */
public class JSCompanyNewsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView childFragment;
    private ArrayList<String> childlist;
    private ArrayAdapter<String> arrayAdapter=null;
    private List<Fragment> fragmentList;
    private FragmentTransaction ft;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.jscompanynews, null);
        childFragment=(ListView)view.findViewById(R.id.child);
        childlist=new ArrayList<String>();
        childlist.add("征拆动态");
        childlist.add("重要讲话");
        childlist.add("会议纪要");
        childlist.add("总经理会议纪要");
        childlist.add("工作督办");
        childlist.add("党群工作");
        childlist.add("公司规范");
        childlist.add("情况通报");
        childlist.add("廉洁");
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new ZCDTFragment());
        fragmentList.add(new ZYJHFragment());
        fragmentList.add(new HYJYFragment());
        fragmentList.add(new ZJLHYJYFragment());
        fragmentList.add(new GZDBFragment());
        fragmentList.add(new DQGZFragment());
        fragmentList.add(new GSGFFragment());
        fragmentList.add(new QKTBFragment());
        fragmentList.add(new LJFragment());
        arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,childlist){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView=(TextView)super.getView(position,convertView,parent);
                textView.setTextSize(14);
                return textView;
            }
        };
        childFragment.setAdapter(arrayAdapter);
        childFragment.setOnItemClickListener(this);
        return  view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ft=getChildFragmentManager().beginTransaction();
        ft.replace(R.id.replace_fragment, fragmentList.get(i));
        ft.commit();
    }
}
