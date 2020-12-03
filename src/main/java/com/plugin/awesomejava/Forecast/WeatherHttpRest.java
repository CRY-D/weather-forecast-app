package com.plugin.awesomejava.Forecast;

import com.plugin.awesomejava.Location.LocationInfo;
import java.util.ArrayList;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import com.plugin.awesomejava.UIApp.DynJLabelObject;
import com.plugin.awesomejava.UIApp.DynamicJLabelList;
import java.util.HashMap;
import javax.swing.ImageIcon;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

public class WeatherHttpRest {
    private static final boolean isMetric = true;
    private static final String DEGREE = "\u00b0";

    private final FeedEntry entry;
    private final WeatherImp weather;
    private ForecastValues forecastValue;

    private HashMap<DynJLabelObject, ForecastValues> WeatherMaphm;

    public WeatherHttpRest(FeedEntry entry, final DynamicJLabelList DynJLabelList) {
        this.entry = entry;
        this.weather = new WeatherImp(DynJLabelList);
        WeatherMaphm = new HashMap<DynJLabelObject, ForecastValues>();
    }

    public HashMap<DynJLabelObject, ForecastValues> HttpRestRequest() {

        final ArrayList<ForecastValues> list = new ArrayList<ForecastValues>();
        final OpenWeatherMap.Units units = (isMetric) ? OpenWeatherMap.Units.METRIC : OpenWeatherMap.Units.IMPERIAL;
        final OpenWeatherMap owm = new OpenWeatherMap(units, entry.getApiKey());

        final byte forecastDays = Byte.valueOf(String.valueOf(entry.getDays()));
        try {

            final DailyForecast forecast = owm.dailyForecastByCityName(entry.getLocation(), entry.getCountryCode(), forecastDays);

            System.out.println("Raw Response: " + forecast.getRawResponse());

            int numForecasts = forecast.getForecastCount();
            for (int i = 0; i < numForecasts; i++) {
                final DailyForecast.Forecast dayForecast = forecast.getForecastInstance(i);

                IntializeForecastValues(forecast.getForecastInstance(i),
                        dayForecast.getTemperatureInstance(),
                        dayForecast.getWeatherInstance(0));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            WeatherMaphm.clear();
        }
        return WeatherMaphm;//epistrofi listas
    }

    private void IntializeForecastValues(final DailyForecast.Forecast dayForecast,
            final DailyForecast.Forecast.Temperature temperature,
            final DailyForecast.Forecast.Weather weather) {
        forecastValue = new ForecastValues();

        forecastValue.setDateInformations("Apudeti e'yasembayo: " + String.valueOf(dayForecast.getDateTime()));

        forecastValue.setMinTemperature(TemperatureRoundSplit.SplitStringValue(temperature.getMinimumTemperature()) + DEGREE + "C");
        forecastValue.setMaxTemperature(TemperatureRoundSplit.SplitStringValue(temperature.getMaximumTemperature()) + DEGREE + "C");
        forecastValue.setDateTemperature(TemperatureRoundSplit.SplitStringValue(temperature.getDayTemperature()) + DEGREE + "C");
//        Humidity to amazzi agali mu mpewo
        forecastValue.setHumidity("Amazzi agali mu mpewo : " + String.valueOf(dayForecast.getHumidity()) + "%");
//        Pressure to Puleesa to Amanyi g'empewo to puleesa
        forecastValue.setPressure("Puleesa: " + String.valueOf(dayForecast.getPressure()) + " mbar ");
        forecastValue.setClouds(String.valueOf(dayForecast.getPercentageOfClouds()) + "%");
//        Wind Speed to Obungi bw'empewo
        forecastValue.setWind_Speed("Obungi bw'empewo : " + String.valueOf(dayForecast.getWindSpeed()) + "m/s");

//        Pattern pattern = Pattern.compile(weather.getWeatherDescription(), Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher("light rain");
//        boolean matchFound = matcher.find();
//        if(matchFound){
//            forecastValue.setDescription("Enkuba entono.");
//        }

        forecastValue.setDescription(weather.getWeatherDescription());
        forecastValue.setDayofWeek(dayForecast.getDateTime());

        this.weather.setForecastValue(forecastValue);
        final int day = this.weather.GetDayCode();

        if (day == LocationInfo.DayCode()) {
            forecastValue.setCurrentDay(true);
            forecastValue.setMainWeatherIcon(this.weather
                    .WeatherIcon(forecastValue.getDescription(), true));
        }

        ImageIcon WeatherIcon = this.weather.WeatherIcon(forecastValue.getDescription());
        forecastValue.setWeatherIcon(WeatherIcon);

        PutMapValues(this.weather.DayOfWeekWeather(day));


        System.out.println(weather.getWeatherDescription());
        System.out.println(forecastValue.toString());

//        added code
//        String weatherDescription = "";
//        rain section
        if(weather.getWeatherDescription().equals("moderate rain")){
//            weatherDescription = "Obukuba bugenda kufuyirira leero";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("Obukuba bugenda kufuyirira leero", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("heavy intensity rain")){
//            weatherDescription = "Enkuba egenda kufudemba leero";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("Enkuba egenda kufudemba leero", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("light rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("very heavy rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("extreme rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("freezing rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("light intensity shower rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("shower rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("heavy intensity rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("ragged shower rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        }
//        clouds section
        else if(weather.getWeatherDescription().equals("overcast clouds")){
//            weatherDescription = "Kazimeera";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("Kazimeera", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("few clouds")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("Obuleerere", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("scattered clouds")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("Kibisse", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("broken clouds")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("Kibisse", 1.0f, false, true);
        }
//        clear sky section
        else if(weather.getWeatherDescription().equals("clear sky")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("Butukula", 1.0f, false, true);
        }
//        thunderstorm section
        else if(weather.getWeatherDescription().equals("thunderstorm with light rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("thunderstorm with rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("thunderstorm with heavy rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("light thunderstorm")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("thunderstorm")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("heavy thunderstorm")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("ragged thunderstorm")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("thunderstorm with light drizzle")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("thunderstorm with drizzle")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("thunderstorm with heavy drizzle")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        }
//        drizzle
        else if(weather.getWeatherDescription().equals("light intensity drizzle")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("Ekimpoowooze", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("drizzle")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("heavy intensity drizzle")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("ragged shower rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("light intensity drizzle rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("drizzle rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("heavy intensity drizzle rain")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("shower rain and drizzle")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("heavy shower rain and drizzle")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        } else if(weather.getWeatherDescription().equals("shower drizzle")){
//            weatherDescription = "";
            TextToSpeech tts = new TextToSpeech();
            tts.speak("", 1.0f, false, true);
        }
    }

    private void PutMapValues(final DynJLabelObject labelObj) {
        WeatherMaphm.put(labelObj, forecastValue);
    }
}
