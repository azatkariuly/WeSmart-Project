package com.example.wesmart;

import android.os.Bundle;

import com.example.wesmart.fragments.DeviceFragment;
import com.example.wesmart.fragments.DevicesFragment;
import com.example.wesmart.fragments.LiveFragment;
import com.example.wesmart.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    Fragment fragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_devices:
                    fragment = new DevicesFragment();
                    break;
                case R.id.navigation_live:
                    fragment = new LiveFragment();
                    break;
                case R.id.navigation_device:
                    fragment = new DeviceFragment();
                    break;
                case R.id.navigation_settings:
                    fragment = new SettingsFragment();
                    break;
            }

            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_live);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new LiveFragment());
    }

    private boolean loadFragment(Fragment fragment1) {
        if (fragment1 != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment1)
                    .commit();

            return true;
        }

        return false;
    }
}
