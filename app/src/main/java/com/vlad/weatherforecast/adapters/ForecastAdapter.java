package com.vlad.weatherforecast.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vlad.weatherforecast.R;
import com.vlad.weatherforecast.databinding.ForecastItemBinding;
import com.vlad.weatherforecast.forecast.DatedWeather;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static java.lang.String.format;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder> {
    private List<DatedWeather> datedWeatherList;

    public ForecastAdapter(List<DatedWeather> datedWeatherList){
        this.datedWeatherList = datedWeatherList;
    }

    public void setWeatherList(List<DatedWeather> datedWeatherList) {
        this.datedWeatherList = datedWeatherList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ForecastAdapter.WeatherViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_item, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ForecastAdapter.WeatherViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load("https://openweathermap.org/img/wn/" +
                        datedWeatherList.get(position).getWeather().get(0).getIconName() + ".png")
                .into(holder.mBinding.forecastImageView);

        holder.mBinding.tvForecastDescription.
                setText(datedWeatherList.get(position).getWeather().get(0).getDescription());
        holder.mBinding.tvWindSpeed.setText(String.format("Wind speed: %.2f m/s",
                datedWeatherList.get(position).getWind().getSpeed()));
        holder.mBinding.tvWindGust.setText(format("Wind gust: %.2f m/s",
                datedWeatherList.get(position).getWind().getGust()));
        holder.mBinding.tvHumidity.setText("Humidity: " + datedWeatherList.get(position).getMain().getHumidity() + " %");
        holder.mBinding.tvPressure.setText(format("Pressure: %s hPa",
                datedWeatherList.get(position).getMain().getPressure()));
        holder.mBinding.tvVisibility.setText(format("Visibility: %s m",
                datedWeatherList.get(position).getVisibility()));
        holder.mBinding.tvForecastTime.setText(datedWeatherList.get(position).getDtTxt().split(" ")[1]);
        holder.mBinding.tvTemperature.setText(format("Temperature: %.2f \u2103",
                datedWeatherList.get(position).getMain().getTemp() - 273.15));
    }

    @Override
    public int getItemCount() {
        return datedWeatherList.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        private ForecastItemBinding mBinding;

        public WeatherViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mBinding = ForecastItemBinding.bind(itemView);
        }
    }
}
