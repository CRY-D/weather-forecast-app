package com.plugin.awesomejava.UIApp;

import com.plugin.awesomejava.Forecast.FeedEntry;
import com.plugin.awesomejava.Forecast.ForecastValues;
import com.plugin.awesomejava.Forecast.WeatherHttpRest;
import com.plugin.awesomejava.Location.Error;
import com.plugin.awesomejava.Location.LocationInfo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.Alert;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import com.plugin.awesomejava.Forecast.TextToSpeech;
import com.plugin.awesomejava.Forecast.AudioPlayer;

public class BackgroundWorker extends SwingWorker<String, Void> {

    private final FeedEntry entrys;
    private Error ErrorCode;
    private final DynamicJLabelList DynJLabelList;

    private final JLabel CurrentTempLabel;
    private final JLabel DayLambel;
    private final JLabel DescriptionLabel;
    private final JLabel DetailedLabel;
    private final JLabel HumidityLabel;
    private final JLabel PressureJLabel;
    private final JLabel MaxTempLabel;
    private final JLabel MinTempLabel;
    private final JLabel WindLabel;
    private final JLabel WeatherDayIcon;
    private final JLabel RefreshIconLabel;

    private TextToSpeech tts = new TextToSpeech();

    public BackgroundWorker(FeedEntry entrys, JLabel CurrentTempLabel, JLabel DayLambel, JLabel DescriptionLabel, JLabel DetailedLabel,
            JLabel HumidityLabel, JLabel MaxTempLabel, JLabel MinTempLabel, JLabel WindLabel, TextToSpeech tts) {
        this.entrys = entrys;
        this.CurrentTempLabel = CurrentTempLabel;
        this.DayLambel = DayLambel;
        this.DescriptionLabel = DescriptionLabel;
        this.DetailedLabel = DetailedLabel;
        this.HumidityLabel = HumidityLabel;
        this.MaxTempLabel = MaxTempLabel;
        this.MinTempLabel = MinTempLabel;
        this.WindLabel = WindLabel;
        this.PressureJLabel = null;
        this.DynJLabelList = null;
        this.WeatherDayIcon = null;
        this.RefreshIconLabel = null;
        this.tts = tts;
    }

    public BackgroundWorker(FeedEntry entrys, DynamicJLabelList DynJLabelList, JLabel CurrentTempLabel, JLabel DayLambel,
            JLabel DescriptionLabel, JLabel DetailedLabel, JLabel HumidityLabel, JLabel PressureJLabel, JLabel MaxTempLabel,
            JLabel MinTempLabel, JLabel WindLabel, JLabel WeatherDayIcon, JLabel RefreshIconLabel, TextToSpeech tts) {
        this.entrys = entrys;
        this.DynJLabelList = DynJLabelList;
        this.CurrentTempLabel = CurrentTempLabel;
        this.DayLambel = DayLambel;
        this.DescriptionLabel = DescriptionLabel;
        this.DetailedLabel = DetailedLabel;
        this.HumidityLabel = HumidityLabel;
        this.PressureJLabel = PressureJLabel;
        this.MaxTempLabel = MaxTempLabel;
        this.MinTempLabel = MinTempLabel;
        this.WindLabel = WindLabel;
        this.WeatherDayIcon = WeatherDayIcon;
        this.RefreshIconLabel = RefreshIconLabel;
        this.tts = tts;
    }

    @Override
    protected String doInBackground() throws Exception {
        this.RefreshIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ring.gif")));

        final WeatherHttpRest doRequest = new WeatherHttpRest(entrys, DynJLabelList);

        final HashMap<DynJLabelObject, ForecastValues> ForecastMap = doRequest.HttpRestRequest();
        if (ForecastMap.isEmpty()) {
            ErrorCode = Error.LISTVALUES;
            return "";
        }
        UpdateStatus(ForecastMap);
        return "";
    }

    private void SetForecastValuesForCurrentDay(final ForecastValues ForecastValue) {
//        this.tts = new TextToSpeech();
        this.CurrentTempLabel.setText(ForecastValue.getDateTemperature());
//        this.DescriptionLabel.setText(ForecastValue.getDescription());
        if(ForecastValue.getDescription().equals("moderate rain")){
//            obukuba bugenda kufuyirira leero for moderate rain
            this.DescriptionLabel.setText("Enkuba nga ssi nyingi");
            this.tts.speak("Enkuba nga ssi nyingi", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("light rain")){
            this.DescriptionLabel.setText("Enkuba entono-tono");
            this.tts.speak("Enkuba entono tono", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("freezing rain")){
            this.DescriptionLabel.setText("Enkuba elimu o'buyogovu");
            this.tts.speak("Enkuba elimu o'buyogovu", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("shower rain")){
            this.DescriptionLabel.setText("Enkuba");
            this.tts.speak("Enkuba", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("heavy intensity rain")){
            this.DescriptionLabel.setText("Enkuba namutikwa");
            this.tts.speak("Enkuba namutikwa", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("overcast clouds")){
            this.DescriptionLabel.setText("Embile e'bikwaafu");
            this.tts.speak("Embile e'bikwaafu", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("few clouds")){
            this.DescriptionLabel.setText("Embile ebitono-tono");
            this.tts.speak("Embile ebitono tono", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("scattered clouds")){
            this.DescriptionLabel.setText("Embile ebyeetade");
            this.tts.speak("Embile ebyeetade", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("broken clouds")){
            this.DescriptionLabel.setText("Embile e'bikutufu-kutufu");
            this.tts.speak("Embile e'bikutufu-kutufu", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("clear sky")){
            this.DescriptionLabel.setText("Eggulu okutali bile");
            this.tts.speak("Eggulu okutali bile", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("thunderstorm with light rain")){
            this.DescriptionLabel.setText("Embuyaga ya laddu n'enkuba entono");
            this.tts.speak("Embuyaga ya laddu n'enkuba entono", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("thunderstorm with rain")){
            this.DescriptionLabel.setText("Embuyaga ya laddu elimu enkuba");
            this.tts.speak("Embuyaga ya laddu elimu enkuba", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("thunderstorm with heavy rain")){
            this.DescriptionLabel.setText("Embuyaga elimu enkuba ennyingi");
            this.tts.speak("Embuyaga elimu enkuba ennyingi", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("light thunderstorm")){
            this.DescriptionLabel.setText("Embuyaga entono-tono");
            this.tts.speak("Embuyaga entono-tono", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("thunderstorm")){
            this.DescriptionLabel.setText("Embuyaga");
            this.tts.speak("Embuyaga", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("heavy thunderstorm")){
            this.DescriptionLabel.setText("Embuyaga eya maanyi");
            this.tts.speak("Embuyaga eya maanyi", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("Ragged thunderstorm")){
            this.DescriptionLabel.setText("Embuyaga eyeetadde-tadde");
            this.tts.speak("Embuyaga eyeetadde-tadde", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("Thunderstorm with light drizzle")){
            this.DescriptionLabel.setText("Embuyaga elimu obukuba obutono");
            this.tts.speak("Embuyaga elimu obukuba obutono", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("light intensity drizzle")){
            this.DescriptionLabel.setText("Obukuba obutono");
            this.tts.speak("Obukuba obutono", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("ragged shower rain")){
            this.DescriptionLabel.setText("Enkuba etali ya mujilano");
            this.tts.speak("Enkuba etali ya mujilano", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("light intensity drizzle rain")){
            this.DescriptionLabel.setText("Obukuba obutono");
            this.tts.speak("Obukuba obutono", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("heavy intensity drizzle rain")){
            this.DescriptionLabel.setText("Enkuba ey'amaanyi");
            this.tts.speak("Enkuba ey'amaanyi", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("shower rain and drizzle")){
            this.DescriptionLabel.setText("Enkuba etali ya mujilano");
            this.tts.speak("Enkuba etali ya mujilano", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("heavy shower rain and drizzle")){
            this.DescriptionLabel.setText("Enkuba ey'amaanyi nga yetadde-tadde");
            this.tts.speak("Enkuba ey'amaanyi nga yetadde-tadde", 1.0f, false, false);
        } else if(ForecastValue.getDescription().equals("shower drizzle")){
            this.DescriptionLabel.setText("Obukuba obuwelako");
            this.tts.speak("Obukuba obuwelako", 1.0f, false, false);
        }
        else {
            this.DescriptionLabel.setText(ForecastValue.getDescription());
            this.tts.speak(ForecastValue.getDescription(), 1.0f, false, false);
        }
        
//        this.DetailedLabel.setText(ForecastValue.getDateInformations());
        this.HumidityLabel.setText(ForecastValue.getHumidity());
        this.PressureJLabel.setText(ForecastValue.getPressure());
        this.WeatherDayIcon.setIcon(ForecastValue.getMainWeatherIcon());
        this.MaxTempLabel.setText(ForecastValue.getMaxTemperature());
        this.MinTempLabel.setText(ForecastValue.getMinTemperature());
        this.WindLabel.setText(ForecastValue.getWind_Speed());
    }

    private void UpdateStatus(final HashMap<DynJLabelObject, ForecastValues> ForecastMap) {

        Set set = ForecastMap.entrySet();
        Iterator iterator2 = set.iterator();
        while (iterator2.hasNext()) {
            final Map.Entry MapEntry = (Map.Entry) iterator2.next();
            final ForecastValues ForecastValue = (ForecastValues) MapEntry.getValue();
            final DynJLabelObject DynLabel = (DynJLabelObject) MapEntry.getKey();

            if (ForecastValue.isCurrentDay()) {
                SetForecastValuesForCurrentDay(ForecastValue);
            }
            DynLabel.getTemperatureJLabel().setText(ForecastValue.getDateTemperature());
            if (ForecastValue.getWeatherIcon() != null) {
                DynLabel.getIconJLabel().setIcon(ForecastValue.getWeatherIcon());
            }
        }
    }

    @Override
    protected void done() {
        if (ErrorCode == Error.LISTVALUES) {
            new JFxBuilder(new DialogObject(Alert.AlertType.ERROR, "Error Dialog", "Ooops, there was an error!",
                    "Something goes wrong. Please check your internet connection first or try again later")).Invoke();
        }
        this.RefreshIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh_15px.png")));
    }
}
