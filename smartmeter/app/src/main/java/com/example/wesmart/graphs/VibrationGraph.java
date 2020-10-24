package com.example.wesmart.graphs;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.wesmart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class VibrationGraph extends Fragment {

    private LineChartView lineChartView;
    private RequestQueue mQueue;

    private ProgressBar mProgress;

    private List xAzat = new ArrayList();
    private List yAzat = new ArrayList();
    private Line line = new Line(yAzat).setColor(Color.parseColor("#69ab1d"));

    private int current_index = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_vibration, container, false);

        mQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        lineChartView = view.findViewById(R.id.chart);
        lineChartView.setZoomEnabled(false);

        mProgress = view.findViewById(R.id.progress_bar);

        jsonParse();


        return view;
    }

    private void jsonParse() {
        long time = System.currentTimeMillis();

        String url = "https://boiling-coast-60811.herokuapp.com/sensor/1?from=" + (time-24*60*60*1000) + "&to=" + time;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            int date_check = 0;
                            for (int i=0; i<response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                xAzat.add(new AxisValue(current_index).setLabel(jsonObject.getString("measuredAt").substring(11, 19)));
                                yAzat.add(new PointValue(current_index,
                                        Integer.parseInt(jsonObject.getString("vibration").split("\\.")[0])));
                                current_index++;
                            }

                            graphing();
                            mProgress.setVisibility(View.INVISIBLE);
                            lineChartView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(jsonArrayRequest);
    }

    private void graphing() {
        List lines = new ArrayList();
        line.setHasPoints(false);
        line.setStrokeWidth(1/10);
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(xAzat);
        axis.setHasTiltedLabels(true);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);

        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());

        /*
        viewport.top = 230;
        viewport.bottom = 210;

        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);

         */




        //Scrollable
        if (xAzat.size() > 200) {
            viewport.left = xAzat.size() - 200;
            viewport.right = xAzat.size();
            lineChartView.setCurrentViewport(viewport);
        }
    }
}
