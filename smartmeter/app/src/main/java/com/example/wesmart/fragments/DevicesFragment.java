package com.example.wesmart.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wesmart.R;
import com.example.wesmart.stomp.StompManager;
import com.example.wesmart.stomp.Telemetry;
import com.github.anastr.speedviewlib.SpeedView;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Objects;

public class DevicesFragment extends Fragment {

    private static final String LOG_TAG = "StompClient";
    private static final String WS_SERVER = "wss://boiling-coast-60811.herokuapp.com/telemetry-websocket/websocket";
    private static final String WS_TOPIC = "/sensor/1/telemetry";

    private static final Gson GSON_CONVERTER = new Gson();

    private StompManager stompManager;

    private SpeedView ageingRateSpeed, vibrationSpeed;
    private TextView agingrateTxt, vibrationTxt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devices, container, false);

        ageingRateSpeed = view.findViewById(R.id.aging_rate_indicator);
        vibrationSpeed = view.findViewById(R.id.vibration_rate_indicator);

        agingrateTxt = view.findViewById(R.id.agingrateTxt);
        vibrationTxt = view.findViewById(R.id.vibrationTxt);

        stompManager = new StompManager(WS_SERVER);
        stompManager.connect();
        stompManager.subscribeTopic(WS_TOPIC, stompMessage -> {
            Log.d(LOG_TAG, stompMessage.getPayload());
            setTelemetry(stompMessage.getPayload());
        }, throwable -> {
            Log.d(LOG_TAG, Objects.requireNonNull(throwable.getMessage()));
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        stompManager.disconnect();
        stompManager = null;
        super.onDestroyView();
    }

    @SuppressLint("SetTextI18n")
    private void setTelemetry(String payload) {
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
            Telemetry telemetry = GSON_CONVERTER.fromJson(payload, Telemetry.class);

            String agingrate_speed = String.valueOf(telemetry.getAgeingRate()).split("\\.")[0];
            String vibration_speed = String.valueOf(telemetry.getVibration()).split("\\.")[0];

            ageingRateSpeed.speedTo(Integer.parseInt(agingrate_speed)/2, 1000);
            agingrateTxt.setText(agingrate_speed + " h/h");

            vibrationSpeed.speedTo(Integer.parseInt(vibration_speed)/2, 1000);
            vibrationTxt.setText(vibration_speed + " mm/s^2");
        });
    }

}
