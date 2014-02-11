package com.vaadin.chartwebinar.backend;


import java.util.Date;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemperatureReading {
    
    private Date date;
    private Integer temperature;
    private MeasurementPlace place;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public MeasurementPlace getPlace() {
        return place;
    }

    public void setPlace(MeasurementPlace place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "TemperatureReading{" + "date=" + date + ", temperature=" + temperature + ", place=" + place + '}';
    }
    
}
