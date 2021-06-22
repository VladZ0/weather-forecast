package com.vlad.weatherforecast.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vlad.weatherforecast.R;
import com.vlad.weatherforecast.adapters.ForecastAdapter;
import com.vlad.weatherforecast.databinding.FragmentMainBinding;
import com.vlad.weatherforecast.forecast.DatedWeather;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainFragment extends Fragment {

    private FragmentMainBinding mBinding;

    public static MainFragment getNewInstance(Bundle args){
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.bind(
                inflater.inflate(R.layout.fragment_main, container, false)
        );
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.recyclerView.setAdapter(
                new ForecastAdapter((List<DatedWeather>) getArguments().getSerializable("weather_list")));
    }
}
