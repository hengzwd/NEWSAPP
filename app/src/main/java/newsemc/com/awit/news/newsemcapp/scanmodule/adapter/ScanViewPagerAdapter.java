package newsemc.com.awit.news.newsemcapp.scanmodule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import newsemc.com.awit.news.newsemcapp.scanmodule.bean.QRCodeBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.fragment.DesignInfoFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.fragment.PierRecordsFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.fragment.SpecialsTructureRecordsFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.fragment.SurveyFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.fragment.TunnelRecordsFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.fragment.PierRecordsMediaFragment;

/**
 * Created by leguang on 2016/9/15 0009.
 */
public class ScanViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = ScanViewPagerAdapter.class.getSimpleName();
    private String[] titleType = {"工程概况", "施工记录", "设计资料","影像资料"};
    private QRCodeBean mQRCodeBean;

    public ScanViewPagerAdapter(FragmentManager fm, QRCodeBean mQRCodeBean) {
        super(fm);
        this.mQRCodeBean = mQRCodeBean;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return SurveyFragment.newInstance();
        } else if (position == 1) {
            if (mQRCodeBean.getType().equals("0")) {
                return PierRecordsFragment.newInstance();
            } else if (mQRCodeBean.getType().equals("1")) {
                return SpecialsTructureRecordsFragment.newInstance();
            } else if (mQRCodeBean.getType().equals("2")) {
                return TunnelRecordsFragment.newInstance();
            } else {
                return null;
            }

        } else if (position == 2) {
            return DesignInfoFragment.newInstance();
        } else if (position==3)
        {
            return PierRecordsMediaFragment.newInstance();
        }else {
            return null;
        }
    }

    @Override
    public int getCount() {
        if (null != titleType) {
                return titleType.length;
        }
        return  0;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (null != titleType) {

            return titleType[position];
        }
        return null;
    }
}
