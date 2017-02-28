package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import newsemc.com.awit.news.newsemcapp.R;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 15-7-9.
 */
public class AppFragment extends Fragment {
//    private ArrayList<View> dots;
//    private ViewPager mViewPager;
//    private ViewPagerAdapter adapter;
//    private View view1, view2, view3;
//    private int oldPosition = 0;// 记录上一次点的位置
//    private int currentItem; // 当前页面
//    private List<View> viewList = new ArrayList<View>();// 把需要滑动的页卡添加到这个list中
public static AppFragment newInstance(){
    AppFragment f = new AppFragment();
    return f;
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.appfragment, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        initPager();
//    }
//
//    public void initPager() {
//        // 添加当前Acitivity到ancivitylist里面去，为了方便统一退出
//        // 显示的点
//        dots = new ArrayList<View>();
//        dots.add(getView().findViewById(R.id.dot_1));
//        dots.add(getView().findViewById(R.id.dot_2));
//        dots.add(getView().findViewById(R.id.dot_3));
//        // 得到viewPager的布局
//        LayoutInflater lf = LayoutInflater.from(getActivity());
//        view1 = lf.inflate(R.layout.mainfragment, null);
//        view2 = lf.inflate(R.layout.mainfragment, null);
//        view3 = lf.inflate(R.layout.mainfragment, null);
//        viewList.add(view1);
//        viewList.add(view2);
//        viewList.add(view3);
//        // 找到点击进入那个按钮
//        mViewPager = (ViewPager)getView(). findViewById(R.id.vp);
//
//        adapter = new ViewPagerAdapter();
//        mViewPager.setAdapter(adapter);
//        dots.get(0).setBackgroundResource(R.drawable.dot_focus);
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                // TODO Auto-generated method stub
//
//                dots.get(oldPosition).setBackgroundResource(
//                        R.drawable.dot_blur);
//                dots.get(position)
//                        .setBackgroundResource(R.drawable.dot_focus);
//
//                oldPosition = position;
//                currentItem = position;
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//        });
//    }
//
//
//
//
//
//    private class ViewPagerAdapter extends PagerAdapter {
//
//        public ViewPagerAdapter() {
//            super();
//            // TODO Auto-generated constructor stub
//            // 得到viewpager切换的三个布局，并把它们加入到viewList里面,记得view用打气筒生成，否则容易出现空指针异常
//
//        }
//
//        @Override
//        public int getCount() {
//            // TODO Auto-generated method stub
//            return viewList.size();
//        }
//
//        // 是否是同一张图片
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            // TODO Auto-generated method stub
//            return view == object;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup view, int position, Object object) {
//            ((ViewPager) view).removeView(viewList.get(position));
//
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup view, int position) {
//            ((ViewPager) view).addView(viewList.get(position));
//            return viewList.get(position);
//        }
//    }
}
