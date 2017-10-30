package com.app.codingtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;


public class LineChartFragment extends Fragment {
    ArrayList<String> xAxis;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        final ViewPager pager = (ViewPager)getActivity().findViewById(R.id.viewPager);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(3);
            }
        });
        FloatingActionButton backFab = (FloatingActionButton)view.findViewById(R.id.back_fab);

        backFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });
        LineChart chart = (LineChart)view.findViewById(R.id.lchart);
        LineData data = new LineData(getXAxisValues(),getDataSet());
        chart.setData(data);
        chart.animateXY(1000, 1000);
        chart.invalidate();
        return view;
    }


    private ArrayList<ILineDataSet> getDataSet() {
        ArrayList<ILineDataSet> dataSets = null;

        ArrayList<Entry> valueSet1 = new ArrayList<>();
        valueSet1.add(new Entry(3200, 0));
        valueSet1.add( new Entry(4500, 1));
        valueSet1.add(new Entry(2200, 2));
        valueSet1.add(new Entry(3000, 3));

        LineDataSet lineDataSet1 = new LineDataSet(valueSet1, "Net Sales");
        lineDataSet1.setColor(Color.rgb(0, 155, 0));

        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

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




