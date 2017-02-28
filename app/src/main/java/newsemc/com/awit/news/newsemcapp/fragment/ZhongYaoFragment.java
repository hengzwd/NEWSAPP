package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.activity.SingleActivity;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.ImgInfoList;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ImgInfoListImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 15-7-9.
 */
public class ZhongYaoFragment extends Fragment implements HttpResultListener,ZrcListView.OnItemClickListener,View.OnClickListener {
    ZrcListView zrcListView;
    private int pageNo = 1;
    private List<ImgInfoList> compwnylist;
    private TextView prp;
    private Button back;
    private String ssid;
    private String account;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.zhongyaowenjianfragment, null);
        //DDDD
        SharedPreferences testspec=getActivity().getSharedPreferences("testlogin", MainActivity.MODE_PRIVATE);
        String special=testspec.getString("test",null);
        Log.i("special：：：：：：",special+"");

        if ("test".equals(special)){
            Log.i("进入if","");
            //获取特殊登录存储的数据
            SharedPreferences SpecialsharedPreferences=getActivity().getSharedPreferences("SPEC", CompanyNewSingleActivity.MODE_PRIVATE);
            ssid=SpecialsharedPreferences.getString("KEY","");
            Log.i("Specialssid", ssid);
            account=SpecialsharedPreferences.getString("ACCOUNT","");
            Log.i("SPECIALACCOUNT", account);
            // finish();

        }else{
            //清空特殊登录所保存的数据
            SharedPreferences.Editor editor=testspec.edit();
            editor.clear();
            editor.commit();
            Log.i("清空特殊登录的数据","进入else。。。。");
            //正常登录过来的
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("SP", MainActivity.MODE_PRIVATE);
            ssid=sharedPreferences.getString("KEY","");
            Log.i("ssid", ssid + "");
            account=sharedPreferences.getString("ACCOUNT","");
            Log.i("ACCOUNT", account + "");
            //finish();


        }

        prp=(TextView)view.findViewById(R.id.prp);
        back=(Button)view.findViewById(R.id.back);
        back.setOnClickListener(ZhongYaoFragment.this);
        prp.setText("公司信息-重要文件");
        ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
        imgInfoListImpl.getImgInfoList(ssid,account, "13", "1", "10", "1");

        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);

        zrcListView = (ZrcListView)view.findViewById(R.id.work);
        zrcListView.setHeadable(header);
        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo = pageNo+1;
                String page = String.valueOf(pageNo);
                Log.i("pageno",pageNo+"");
                ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(ZhongYaoFragment.this);
                imgInfoListImpl.getImgInfoList(ssid,account, "13", page, "10", "2");
                zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                zrcListView.startLoadMore();
            }
        });

        return  view;
    }
    private class WorkAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        //private String[] data = {"总","关于开展2015年5月份月度专项检查的通知","05-06","50人已读"};
        private List<ImgInfoList> data;

        class ViewHolder{
            TextView title;
            TextView time;
            TextView read;
        }

        public WorkAdapter(Context context,List<ImgInfoList> data){
            mInflater = LayoutInflater.from(context);
            this.data=data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ImgInfoList m = (ImgInfoList)this.getItem(position);
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.work_list_item,null);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.time= (TextView)convertView.findViewById(R.id.time);
                holder.read = (TextView)convertView.findViewById(R.id.read);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.title.setText(m.getInfoname());
            holder.time.setText(m.getInfodate());
            holder.read.setText(m.getNum());
            return convertView;
        }
    }

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onResult(Object obj) {
        if(obj instanceof FailRequest){
            FailRequest  fail= (FailRequest)obj;
            if(!(fail==null)){
                System.out.println("异常信息："+fail.getMsg());
                System.out.println("状态："+fail.getStatus());
            }
        }else{
            //通知公告
            compwnylist=(List<ImgInfoList>)obj;
            if(compwnylist.size()>0){
                zrcListView.setAdapter(new WorkAdapter(getActivity(),compwnylist));
                zrcListView.setOnItemClickListener(this);
            }else{
                ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(ZhongYaoFragment.this);
                imgInfoListImpl.getImgInfoList(ssid,account, "13",
                        pageNo>1?String.valueOf(pageNo-1):String.valueOf(pageNo), "10", "2");
            }

        }
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onItemClick(ZrcListView parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.setClass(getActivity(), SingleActivity.class);
        intent.putExtra("infoid", compwnylist.get(position).getInfoid() + "");
//        Log.i("infoid", compwnylist.get(position).getInfoid());
        intent.putExtra("infotype", compwnylist.get(position).getInfotype());
        startActivityForResult(intent,0);
    }

    @Override
    public void onClick(View view) {
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(pageNo==1){
            ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
            imgInfoListImpl.getImgInfoList(ssid,account, "13", "1", "10", "1");
        }else{
            ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
            imgInfoListImpl.getImgInfoList(ssid,account, "13", pageNo+"", "10", "2");
        }
    }
}
