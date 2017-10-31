package com.coolweather.android;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coolweather.android.gson.Daily_forecast;
import com.coolweather.android.gson.Hourly_forecast;
import com.coolweather.android.gson.Weather;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout hourly_forecastlayout;
    private LinearLayout daily_forecastlayout;
    private TextView airText;
    private TextView comfText;
    private TextView cwText;
    private TextView drsgText;
    private TextView fluText;
    private TextView sportText;
    private TextView travText;
    private TextView uvText;

    private ImageView bingPicImg;

    public SwipeRefreshLayout swipeRefresh;
    private String mWeatherId;
    public DrawerLayout drawerLayout;
    private Button navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decView = getWindow().getDecorView();
            decView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        //初始化控件
        weatherLayout = (ScrollView)findViewById(R.id.weather_layout);
        titleCity = (TextView)findViewById(R.id.title_city);
        titleUpdateTime = (TextView)findViewById(R.id.title_update_time);
        degreeText = (TextView)findViewById(R.id.degree_text);
        weatherInfoText = (TextView)findViewById(R.id.weather_info_text);
        hourly_forecastlayout = (LinearLayout)findViewById(R.id.hourly_forecast_layout);
        daily_forecastlayout = (LinearLayout)findViewById(R.id.daily_forecast_layout);
        airText = (TextView)findViewById(R.id.airtext);
        comfText = (TextView)findViewById(R.id.comftext);
        cwText = (TextView)findViewById(R.id.cwtext);
        drsgText = (TextView)findViewById(R.id.drsgtext);
        fluText = (TextView)findViewById(R.id.flutext);
        sportText = (TextView)findViewById(R.id.sporttext);
        travText = (TextView)findViewById(R.id.travtext);
        uvText = (TextView)findViewById(R.id.uvtext);
        bingPicImg = (ImageView)findViewById(R.id.bing_pic_img);
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navButton = (Button)findViewById(R.id.nav_button);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        if (weatherString!=null){
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        }else {
            mWeatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        String bingPic = prefs.getString("bing_pic",null);
        if (bingPic!=null){
            //Glide.with(this).load(bingPic).into(bingPicImg);
        }else {
            //loadBingPic();
        }
    }

    public void requestWeather(final String weatherId) {
        String url = "https://free-api.heweather.com/v5/weather?city="
                + weatherId + "&key=835fc46391804935bcb85041144bf14b";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        //loadBingPic();
    }

    private void showWeatherInfo(Weather weather){
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[0];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.cond.weathertxt;
        List<Hourly_forecast> hourly_forecastLists = weather.hourly_forecastList;
        List<Daily_forecast> daily_forecastLists = weather.daily_forecastList;
        String airTexts = weather.suggestion.airquality.brieftxt;
        String comfTexts = weather.suggestion.comfort.brieftxt;
        String cwTexts = weather.suggestion.carWash.brieftxt;
        String drsgTexts = weather.suggestion.dressindex.brieftxt;
        String fluTexts = weather.suggestion.coldindex.brieftxt;
        String sportTexts = weather.suggestion.sport.brieftxt;
        String travTexts = weather.suggestion.tourindex.brieftxt;
        String uvTexts = weather.suggestion.uvindex.brieftxt;

        titleCity.setText(weather.basic.cntyName+"  -  "+cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        airText.setText("空气质量指数:"+airTexts);
        comfText.setText("舒适度指数:"+comfTexts);
        cwText.setText("洗车指数:"+cwTexts);
        drsgText.setText("穿衣指数:"+drsgTexts);
        fluText.setText("感冒指数:"+fluTexts);
        sportText.setText("运动指数:"+sportTexts);
        travText.setText("旅游指数:"+travTexts);
        uvText.setText("紫外线指数:"+uvTexts);

        hourly_forecastlayout.removeAllViews();
        if (hourly_forecastLists!=null){
            for (Hourly_forecast hourly_forecast : hourly_forecastLists){
                View view = LayoutInflater.from(this).inflate(R.layout.hourly_forecast_item,hourly_forecastlayout,false);
                TextView dateTimetext = (TextView) view.findViewById(R.id.dateTime_text);
                TextView hourInfotext = (TextView) view.findViewById(R.id.hour_Info_text);
                TextView hourTmptext = (TextView) view.findViewById(R.id.hour_tmp_text);
                TextView hourHumtext = (TextView) view.findViewById(R.id.hour_hum_text);
                //Log.e("++++++++++++",hourly_forecast.dateTime);
                dateTimetext.setText(hourly_forecast.dateTime.split(" ")[1]);
                hourInfotext.setText(hourly_forecast.cond.weathertxt);
                hourTmptext.setText(hourly_forecast.temperature+"℃");
                hourHumtext.setText(hourly_forecast.humidity+"%RH");
                hourly_forecastlayout.addView(view);
            }
        }
        daily_forecastlayout.removeAllViews();
        if (daily_forecastLists!=null){
            for (Daily_forecast daily_forecast : daily_forecastLists){
                View view = LayoutInflater.from(this).inflate(R.layout.daily_forecast_item,daily_forecastlayout,false);
                TextView datetext = (TextView)view.findViewById(R.id.date_text);
                TextView dailyInfotext = (TextView)view.findViewById(R.id.daily_Info_text);
                TextView dailyTmpmaxtext = (TextView)view.findViewById(R.id.daily_tmpmax_text);
                TextView dailyTmpmintext = (TextView)view.findViewById(R.id.daily_tmpmin_text);
                TextView dailyHumtext = (TextView)view.findViewById(R.id.daily_hum_text);
                datetext.setText(daily_forecast.date);
                dailyInfotext.setText(daily_forecast.cond.dayweathertxt);
                dailyHumtext.setText(daily_forecast.humidity+"%RH");
                dailyTmpmaxtext.setText(daily_forecast.tmp.maxtem+"℃");
                dailyTmpmintext.setText(daily_forecast.tmp.mintem+"℃");
                daily_forecastlayout.addView(view);
            }
        }
        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void loadBingPic(){
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }
}
