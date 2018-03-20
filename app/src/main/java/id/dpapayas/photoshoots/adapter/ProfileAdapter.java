package id.dpapayas.photoshoots.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import id.dpapayas.photoshoots.fragment.FeaturedFragment;
import id.dpapayas.photoshoots.fragment.RecentFragment;

/**
 * Created by dpapayas on 9/6/17.
 */

public class ProfileAdapter extends FragmentPagerAdapter {
    private List<String> mDataList;

    public ProfileAdapter(FragmentManager fm, List<String> mDataList) {
        super(fm);
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new RecentFragment();
            case 1:
                return new FeaturedFragment();
            default:
                return null;
        }

    }

}