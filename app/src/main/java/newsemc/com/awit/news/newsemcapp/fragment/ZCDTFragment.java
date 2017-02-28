package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.activity.SingleActivity;
import newsemc.com.awit.news.newsemcapp.adapter.MainNewsAdapter;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.ImgInfoList;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ImgInfoListImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by cll on 2015/10/13.
 */
public class ZCDTFragment extends Fragment implements HttpResultListener,ZrcListView.OnItemClickListener{
    private ZrcListView zrcListView;
//    private TextView textView;
    private int pageNo=1;
    private String ssid,account;
    private List<ImgInfoList> imgInfoListList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=(View)inflater.inflate(R.layout.jspubliclistshow, null);
        //DDDD
//        SharedPreferences testspec=getActivity().getSharedPreferences("testlogin", MainActivity.MODE_PRIVATE);
//        String special=testspec.getString("test",null);
//        Log.i("special：：：：：：",special);
//
//        if ("test".equals(special)){
//            Log.i("进入if","");
//            //获取特殊登录存储的数据
//            SharedPreferences SpecialsharedPreferences=getActivity().getSharedPreferences("SPEC", CompanyNewSingleActivity.MODE_PRIVATE);
//            ssid=SpecialsharedPreferences.getString("KEY","");
//            Log.i("Specialssid", ssid);
//            account=SpecialsharedPreferences.getString("ACCOUNT","");
//            Log.i("SPECIALACCOUNT", account);
//            // finish();
//
//        }else{
//            //清空特殊登录所保存的数据
//            SharedPreferences.Editor editor=testspec.edit();
//            editor.clear();
//            editor.commit();
//            Log.i("清空特殊登录的数据","进入else。。。。");
//            //正常登录过来的
//            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("SP", MainActivity.MODE_PRIVATE);
//            ssid=sharedPreferences.getString("KEY","");
//            Log.i("ssid", ssid + "");
//            account=sharedPreferences.getString("ACCOUNT","");
//            Log.i("ACCOUNT", account + "");
//            //finish();
//
//
//        }
        //获取用户名和ssid
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("SP", MainActivity.MODE_PRIVATE);
        ssid=sharedPreferences.getString("KEY","");
        Log.i("ssid", ssid + "");
        account=sharedPreferences.getString("ACCOUNT","");
        Log.i("ACCOUNT", account + "");

        //主界面公司新闻列表
        ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
        imgInfoListImpl.getImgInfoList(ssid,account, "10", "1", "10", "1");

        //下拉刷新事件响应
        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        zrcListView=(ZrcListView)view.findViewById(R.id.publiclistview);
        zrcListView.setHeadable(header);
        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo = pageNo + 1;
                Log.i("pageno", pageNo + "");
                String page = String.valueOf(pageNo);
                ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(ZCDTFragment.this);
                imgInfoListImpl.getImgInfoList(ssid, account, "10", page, "10", "2");
                zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                zrcListView.startLoadMore();
            }
        });
//        textView=(TextView)view.findViewById(R.id.no);
//        textView.setText("征拆动态");
        return view;
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
            imgInfoListList = (List<ImgInfoList>) obj;
            if(imgInfoListList.size()>0){
                zrcListView.setAdapter(new WorkAdapter(getActivity(),imgInfoListList));
                zrcListView.setOnItemClickListener(ZCDTFragment.this);
            }else{
                ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
                imgInfoListImpl.getImgInfoList(ssid,account, "10",
                        pageNo>1?String.valueOf(pageNo-1):String.valueOf(pageNo), "10", "1");
            }
        }
    }

    @Override
    public void onFinish() {

    }

    //下拉刷新listview点击事件
    @Override
    public void onItemClick(ZrcListView parent, View view, int position, long id) {
        Log.i("position", position + "");
        Intent intent=new Intent();
        intent.setClass(getActivity(), SingleActivity.class);
        intent.putExtra("infoid", imgInfoListList.get(position - 1).getInfoid() + "");
        intent.putExtra("infotype", imgInfoListList.get(position-1).getInfotype());
        startActivityForResult(intent, 0);
    }

    /**
     * 点击详情增加访问量
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(pageNo==1){
            ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
            imgInfoListImpl.getImgInfoList(ssid, account, "10", pageNo + "", "10", "1");
        }else{
            ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
            imgInfoListImpl.getImgInfoList(ssid, account, "10", pageNo + "", "10", "2");
        }

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
}
