package com.app.codingtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import java.util.ArrayList;


public class HorizontalBarChartFragment extends Fragment {
    ArrayList<String> xAxis;
    ArrayList<BarDataSet> dataSets;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_horizontal_bar_chart, container, false);
        final ViewPager pager = (ViewPager)getActivity().findViewById(R.id.viewPager);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        FloatingActionButton backFab = (FloatingActionButton)view.findViewById(R.id.back_fab);

        backFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });
        HorizontalBarChart chart = (HorizontalBarChart)view.findViewById(R.id.hchart);
        BarData data = new BarData(getXAxisValues(),getDataSet());
        chart.setData(data);
        chart.animateXY(1000, 1000);
        chart.invalidate();
        return view;
    }


    private ArrayList<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(3200, 0);
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(4500, 1);
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(2200, 2);
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(3000, 3);
        valueSet1.add(v1e4);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(2200, 0);
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(3300, 1);
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(1500, 2);
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(2000, 3);
        valueSet2.add(v2e4);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Net Sales");
        int myColor = Color.parseColor("#FF7F50");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Gross Margin");
        barDataSet2.setColor(myColor);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        xAxis = new ArrayList<>();
        xAxis.add("2013");
        xAxis.add("2014");
        xAxis.add("2015");
        xAxis.add("2016");
        return xAxis;
    }
}




