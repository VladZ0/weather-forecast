package com.vlad.weatherforecast.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vlad.weatherforecast.forecast.DatedWeather;
import com.vlad.weatherforecast.ui.MainFragment;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ForecastPagerAdapter extends FragmentStateAdapter {
    private List<DatedWeather> datedWeatherList;

    private int lastIndexOfFirstDay = 0, firstIndexOfLastDay = 0;

    public ForecastPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ForecastPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity,
                                List<DatedWeather> datedWeatherList) {
        super(fragmentActivity);
        this.datedWeatherList = datedWeatherList;
    }

    public void setWeatherList(List<DatedWeather> datedWeatherList) {
        this.datedWeatherList = datedWeatherList;
        notifyDataSetChanged();
    }

    public List<DatedWeather> getWeatherList() {
        return datedWeatherList;
    }

    public int getFirstIndexOfLastDay() {
        return firstIndexOfLastDay;
    }

    public int getLastIndexOfFirstDay() {
        return lastIndexOfFirstDay;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            Bundle args = new Bundle();
            args.putSerializable("weather_list",
                    new ArrayList<>(datedWeatherList.subList(0, lastIndexOfFirstDay + 1)));
            return MainFragment.getNewInstance(args);
        }
        if(position == (datedWeatherList.size() / 8) - 1){
            Bundle args = new Bundle();
            args.putSerializable("weather_list",
                    new ArrayList<>(datedWeatherList.subList(firstIndexOfLastDay, datedWeatherList.size())));
            return MainFragment.getNewInstance(args);
        }

        Bundle args = new Bundle();
        args.putSerializable("weather_list",
                new ArrayList<>(datedWeatherList.subList(lastIndexOfFirstDay + (8 * (position - 1)) + 1,
                        lastIndexOfFirstDay + (8 * position) + 1)));
        return MainFragment.getNewInstance(args);
    }

    @Override
    public int getItemCount() {
        return getDaysCount();
    }

    private int getDaysCount(){
        if(datedWeatherList != null) {
            if(datedWeatherList.size() == 1){
                lastIndexOfFirstDay = 0;
                return 1;
            }
            else{
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                int t1 = 3600000;
                int t2 = 7200000;
                for (int i = 0; i < datedWeatherList.size(); i++) {
                    if (    sdf
                            .format(new Date(datedWeatherList.get(i).getDt())).equals("21:00:00")
                            || sdf
                            .format(new Date(datedWeatherList.get(i).getDt() - t1)).equals("21:00:00")
                            || sdf
                            .format(new Date(datedWeatherList.get(i).getDt() - t2)).equals("21:00:00")){

                        lastIndexOfFirstDay = i;
                        firstIndexOfLastDay = i + 8 * ((datedWeatherList.size() - (i+1)) / 8);

                        return 5;
                    }
                }
            }
        }

        return 0;
    }
}
