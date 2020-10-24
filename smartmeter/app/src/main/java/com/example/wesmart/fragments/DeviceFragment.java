package com.example.wesmart.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wesmart.graphs.PowerGraph;
import com.example.wesmart.graphs.VibrationGraph;
import com.example.wesmart.graphs.AgingRateGraph;
import com.example.wesmart.graphs.LoadForecastGraph;
import com.example.wesmart.R;

public class DeviceFragment extends Fragment {

    private Button powerBtn, agingRateBtn, vibrationBtn, loadForecastBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device, container, false);

        loadFragment(new PowerGraph());

        powerBtn = view.findViewById(R.id.btn_power);
        agingRateBtn = view.findViewById(R.id.btn_aging_rate);
        vibrationBtn = view.findViewById(R.id.btn_vibration);
        loadForecastBtn = view.findViewById(R.id.btn_load_forecasting);

        powerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBtnColor(1);
                loadFragment(new PowerGraph());
            }
        });

        agingRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBtnColor(2);
                loadFragment(new AgingRateGraph());
            }
        });

        vibrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBtnColor(3);
                loadFragment(new VibrationGraph());
            }
        });

        loadForecastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBtnColor(4);
                loadFragment(new LoadForecastGraph());
            }
        });

        return view;
    }


    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container1, fragment)
                    .commit();

        }
    }

    private void changeBtnColor(int i) {

        if (i==1) {
            powerBtn.setBackgroundColor(Color.parseColor("#69ab1d"));
            powerBtn.setTextColor(Color.parseColor("#ffffff"));
        } else {
            powerBtn.setBackgroundColor(Color.parseColor("#bdbdbd"));
            powerBtn.setTextColor(Color.parseColor("#000000"));
        }

        if (i==2) {
            agingRateBtn.setBackgroundColor(Color.parseColor("#69ab1d"));
            agingRateBtn.setTextColor(Color.parseColor("#ffffff"));
        } else {
            agingRateBtn.setBackgroundColor(Color.parseColor("#bdbdbd"));
            agingRateBtn.setTextColor(Color.parseColor("#000000"));
        }

        if (i==3) {
            vibrationBtn.setBackgroundColor(Color.parseColor("#69ab1d"));
            vibrationBtn.setTextColor(Color.parseColor("#ffffff"));
        } else {
            vibrationBtn.setBackgroundColor(Color.parseColor("#bdbdbd"));
            vibrationBtn.setTextColor(Color.parseColor("#000000"));
        }

        if (i==4) {
            loadForecastBtn.setBackgroundColor(Color.parseColor("#69ab1d"));
            loadForecastBtn.setTextColor(Color.parseColor("#ffffff"));
        } else {
            loadForecastBtn.setBackgroundColor(Color.parseColor("#bdbdbd"));
            loadForecastBtn.setTextColor(Color.parseColor("#000000"));
        }
    }
}
