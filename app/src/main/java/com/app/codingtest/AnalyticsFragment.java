package com.app.codingtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AnalyticsFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);

        ViewPager pager = (ViewPager)view. findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getActivity(),getChildFragmentManager()));
        return view;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentActivity activity, FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0:
                    BarChartFragment barChartFragment = new BarChartFragment();
                    return barChartFragment;
                case 1:
                    HorizontalBarChartFragment horizontalBarChartFragment = new HorizontalBarChartFragment();
                    return horizontalBarChartFragment;
                case 2:
                    LineChartFragment lineChartFragment = new LineChartFragment();
                    return lineChartFragment;
                case 3:
                    DuoLineChartFragment duoLineChartFragment = new DuoLineChartFragment();
                    return duoLineChartFragment;
                case 4:
                    PieChartFragment pieChartFragment = new PieChartFragment();
                    return pieChartFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}



