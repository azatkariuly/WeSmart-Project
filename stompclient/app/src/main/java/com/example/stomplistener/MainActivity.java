package com.example.stomplistener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "StompClient";
    private static final String WS_SERVER = "wss://boiling-coast-60811.herokuapp.com/telemetry-websocket/websocket";
    private static final String WS_TOPIC = "/sensor/1/telemetry";
    public String myQuery = "/sensor/1?from=";

    private StompManager stompManager;
    private TextView textView;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        //stompManager = new StompManager(WS_SERVER);
        //stompManager.connect();

        /*
        stompManager.subscribeTopic(WS_TOPIC, stompMessage -> {
            print(stompMessage.getPayload());
        }, throwable -> {
            Log.d(LOG_TAG, Objects.requireNonNull(throwable.getMessage()));
        });
        */

        /*
        OkHttpClient client = new OkHttpClient();

        String url = "https://boiling-coast-60811.herokuapp.com/sensor/1?from=1592306982212&to=1592306985222";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(myResponse);
                        }
                    });
                }
            }
        });


         */

        //mQueue = Volley.newRequestQueue(this);
        jsonParse();

        //textView.setText("" +  System.currentTimeMillis());
/*
        GraphView graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        graph.addSeries(series);

 */

    }

    private void jsonParse() {
        long time = System.currentTimeMillis();

        String url = "https://boiling-coast-60811.herokuapp.com/sensor/1?from=" + (time-10000) + "&to=" + time;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        /*for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            String current = jsonObject.getString("current");
                            textView.append(current);
                        }

                         */
                        textView.append("" + response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onDestroy() {
        //stompManager.disconnect();
        //stompManager = null;
        super.onDestroy();
    }

    private void print(String text) {
        runOnUiThread(() -> {
            Log.d(LOG_TAG, text);
            textView.setText(text);
        });
    }
}
