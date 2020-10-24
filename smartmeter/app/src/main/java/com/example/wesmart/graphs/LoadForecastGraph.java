package com.example.wesmart.graphs;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wesmart.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class LoadForecastGraph extends Fragment {

    LineChartView lineChartView;

    String[] axisData = {"2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016",
            "2017", "2018", "2019"};
    int[] yAxisData = {1510, 1520, 1545, 1530, 1560, 1525, 1515, 1540, 1545, 1590, 1510, 1518};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_load_forecasting, container, false);

        lineChartView = view.findViewById(R.id.chart);

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#69ab1d"));

        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);

        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 1600;
        viewport.bottom = 1500;

        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);

        return view;
    }
}
