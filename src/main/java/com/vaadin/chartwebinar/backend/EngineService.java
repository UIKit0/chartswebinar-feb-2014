/*
 * Copyright 2014 mattitahvonenitmill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vaadin.chartwebinar.backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;

@Singleton
public class EngineService {

    private final Random r = new Random();
    private Timer timer;
    
    @PostConstruct
    void startThread() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            
            int lastMain = 70;
            int latPre = 30;
            int lastPost = 50;

            @Override
            public void run() {
                TemperatureReading temperatureReading = new TemperatureReading();
                final MeasurementPlace place = MeasurementPlace.values()[r.nextInt(3)];
                temperatureReading.setPlace(place);
                if(place == MeasurementPlace.ENGINE) {
                    lastMain = adjust(lastMain);
                    temperatureReading.setTemperature(lastMain);
                } else if(place == MeasurementPlace.POSTPROCESSING) {
                    lastPost = adjust(lastPost);
                    temperatureReading.setTemperature(lastPost);
                } else {
                    latPre = adjust(latPre);
                    temperatureReading.setTemperature(latPre);
                }
                    
                temperatureReading.setDate(new Date());
                observers.stream().forEach((realTimeObserver) -> {
                    realTimeObserver.onTemperatureReading(temperatureReading);
                });
            }

            private int adjust(int value) {
                if(r.nextBoolean()) {
                    value += r.nextInt(5);
                } else {
                    value -= r.nextInt(5);
                }
                return value;
            }
        }, 1000, 1000);
    }

    public List<TemperatureReading> getDailyReadings() {
        Calendar cal = Calendar.getInstance();
        List<TemperatureReading> dailyReadings = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (MeasurementPlace mp : MeasurementPlace.values()) {
                TemperatureReading temperatureReading = new TemperatureReading();
                temperatureReading.setPlace(mp);
                temperatureReading.setDate(cal.getTime());
                temperatureReading.setTemperature(r.nextInt(100));
                dailyReadings.add(0,temperatureReading);
            }
            cal.roll(Calendar.DATE, false);
        }
        return dailyReadings;
    }

    public TemperatureReading getCurrentTemperature(MeasurementPlace measurementPlace) {
        TemperatureReading temperatureReading = new TemperatureReading();
        temperatureReading.setPlace(measurementPlace);
        temperatureReading.setDate(new Date());
        temperatureReading.setTemperature(r.nextInt(100));
        return temperatureReading;
    }
    
    public interface RealTimeObserver {
        void onTemperatureReading(TemperatureReading reading);
    }
    
    private final List<RealTimeObserver> observers = new ArrayList<>();
    
    public void addRealTimeObserver(RealTimeObserver observer) {
        observers.add(observer);
    }
    
    public void removeRealTimeObserver(RealTimeObserver observer) {
        observers.remove(observer);
    }
    
    @PreDestroy
    void stopThread() {
        timer.cancel();
    }
    
}
