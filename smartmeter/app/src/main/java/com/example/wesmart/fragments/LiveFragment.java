package com.example.wesmart.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.wesmart.R;
import com.example.wesmart.stomp.StompManager;
import com.example.wesmart.stomp.Telemetry;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Objects;

public class LiveFragment extends Fragment {
    private static final String LOG_TAG = "StompClient";
    private static final String WS_SERVER = "wss://boiling-coast-60811.herokuapp.com/telemetry-websocket/websocket";
    private static final String WS_TOPIC = "/sensor/1/telemetry";

    private static final Gson GSON_CONVERTER = new Gson();

    private ImageView onIndicatorImg, offIndicatorImg;
    private TextView onIndicatorText, offIndicatorText;

    private TextView voltageText, currentText, powerText, powerFactorText;

    private StompManager stompManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);

        onIndicatorText = view.findViewById(R.id.on);
        offIndicatorText = view.findViewById(R.id.off);

        voltageText = view.findViewById(R.id.voltage);
        currentText = view.findViewById(R.id.current);

        powerFactorText = view.findViewById(R.id.power_factor);
        powerText = view.findViewById(R.id.power);

        onIndicatorImg = view.findViewById(R.id.indicator3);
        offIndicatorImg = view.findViewById(R.id.indicator);

        offIndicatorImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnAlertOn();
            }
        });

        onIndicatorImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnAlertOff();
            }
        });

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

    public void turnAlertOn() {
        onIndicatorText.setTextColor(Color.parseColor("#000000"));
        offIndicatorText.setTextColor(Color.parseColor("#495458"));

        onIndicatorImg.setVisibility(View.VISIBLE);
        offIndicatorImg.setVisibility(View.INVISIBLE);
    }

    public void turnAlertOff() {
        onIndicatorText.setTextColor(Color.parseColor("#495458"));
        offIndicatorText.setTextColor(Color.parseColor("#000000"));

        onIndicatorImg.setVisibility(View.INVISIBLE);
        offIndicatorImg.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private void setTelemetry(String payload) {
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
            Telemetry telemetry = GSON_CONVERTER.fromJson(payload, Telemetry.class);

            String volt = String.valueOf(telemetry.getVoltage());
            voltageText.setText(volt.split("\\.")[0] + "." + volt.split("\\.")[1].substring(0, 3) + " V");

            String cur = String.valueOf(telemetry.getCurrent());
            currentText.setText(cur.split("\\.")[0] + "." + cur.split("\\.")[1].substring(0, 1) + " A");

            String powerFactor = String.valueOf(telemetry.getCosphi());
            powerFactorText.setText(powerFactor.split("\\.")[0] + "." + powerFactor.split("\\.")[1].substring(0, 3));

            String power = String.valueOf(telemetry.getPower());
            powerText.setText(power.split("\\.")[0] + " Watts");
        });
    }
}
