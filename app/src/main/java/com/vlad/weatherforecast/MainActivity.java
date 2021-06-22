package com.vlad.weatherforecast;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;
import com.vlad.weatherforecast.adapters.ForecastPagerAdapter;
import com.vlad.weatherforecast.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private MainActivityViewModel mViewModel;
    private ForecastPagerAdapter adapter;

    private int forecastTimeFilter = Utils.FORECAST_PER_THREE_HOURS;
    private int forecastDateLocaleFilter = Utils.FORECAST_CITY_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);

        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        adapter = new ForecastPagerAdapter(this);

        mBinding.searchBtn.setOnClickListener(v -> mViewModel
                .initForecastData(mBinding.etSearch.getText().toString().trim(),
                        forecastTimeFilter, forecastDateLocaleFilter));

        mViewModel.getForecastMutableLiveData().observe(this, forecast -> {
            if(forecast != null){
                adapter.setWeatherList(forecast.getWeatherList());
                mBinding.viewPager.setAdapter(adapter);
                initTabLayout(adapter);
            }
        });

        mViewModel.getTitleMutableLiveData().observe(this, s -> {
            if(!s.equals("")) {
                getSupportActionBar().setTitle(s);
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        forecastTimeFilter = savedInstanceState.getInt("forecastTimeFilter");
        forecastDateLocaleFilter = savedInstanceState.getInt("forecastDateLocaleFilter");
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("forecastTimeFilter", forecastTimeFilter);
        outState.putInt("forecastDateLocaleFilter", forecastDateLocaleFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filters_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.filters){
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.filters_dialog);

            Spinner forecastTimeSpinner = dialog.findViewById(R.id.forecast_time_spinner);
            Spinner dateLocaleSpinner = dialog.findViewById(R.id.date_locale_spinner);
            forecastTimeSpinner.setSelection(forecastTimeFilter);
            dateLocaleSpinner.setSelection(forecastDateLocaleFilter);

            Button dialogCancelBtn = dialog.findViewById(R.id.dialog_cancel_btn);
            dialogCancelBtn.setOnClickListener(v -> dialog.dismiss());

            Button dialogChangeBtn = dialog.findViewById(R.id.dialog_change_btn);
            dialogChangeBtn.setOnClickListener(v -> {
                forecastTimeFilter = forecastTimeSpinner.getSelectedItemPosition();
                forecastDateLocaleFilter = dateLocaleSpinner.getSelectedItemPosition();
                String title = mBinding.etSearch.getText().toString();
                if(title.length() == 0){
                    title = Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()).getTitle()).toString();
                }

                mViewModel.initForecastData(title, forecastTimeFilter, forecastDateLocaleFilter);
                dialog.dismiss();
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initTabLayout(ForecastPagerAdapter adapter){
        new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager, (tab, position) -> {
            if(position == 0){
                tab.setText(adapter.getWeatherList().get(0).getDtTxt().split(" ")[0]);
            }
            else if(position == (adapter.getWeatherList().size() / 8) - 1){
                tab.setText(adapter.getWeatherList()
                        .get(adapter.getFirstIndexOfLastDay()).getDtTxt().split(" ")[0]);
            }
            else{
                tab.setText(adapter.getWeatherList()
                        .get(adapter.getLastIndexOfFirstDay() + (8 * (position - 1)) + 1)
                        .getDtTxt().split(" ")[0]);
            }
        }).attach();
        mBinding.tabLayout.setVisibility(View.VISIBLE);
    }
}