package newsemc.com.awit.news.newsemcapp.scanmodule.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.socks.library.KLog;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.base.BaseActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.ScanViewPagerAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.QRCodeBean;


public class ScanMainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ScanViewPagerAdapter mAdapter;
    private QRCodeBean mQRCodeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_main);
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.vp);

    }

    private void initData() {



        mQRCodeBean = (QRCodeBean) getIntent().getSerializableExtra("QRCodeBean");

        KLog.e("getType:" + mQRCodeBean.getType());
        KLog.e("getName:" + mQRCodeBean.getName());
        KLog.e("getId:" + mQRCodeBean.getId());

        initToolbarBackNavigation(mToolbar);
        setAdapter();
    }

    protected void initToolbarBackNavigation(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanMainActivity.this.onBackPressed();
            }
        });
        }

     private void setAdapter() {
         mAdapter = new ScanViewPagerAdapter(getSupportFragmentManager(), mQRCodeBean);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
     }
}
