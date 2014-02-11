package com.vaadin.chartwebinar.view;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.cdi.CDIView;
import com.vaadin.chartwebinar.backend.EngineService;
import com.vaadin.chartwebinar.backend.TemperatureReading;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import java.util.Date;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView
public class DefaultView extends AbstractView {
    
    DataSeries main = new DataSeries();
    DataSeries pre = new DataSeries();
    DataSeries post = new DataSeries();
    Chart chart = new Chart();
    
    @Inject
    private EngineService engineService;
    
    @PostConstruct
    void setup() {
        chart.setCaption("All temperatures readings for last week:");
        main.setName("@ Engine");
        pre.setName("@ Pre processing");
        post.setName("@ Post processing");
        chart.getConfiguration().setSeries(main, pre, post);
        chart.getConfiguration().getxAxis().setType(AxisType.DATETIME);
        chart.addChartClickListener(e->{
            main.add(new DataSeriesItem(new Date(), e.getyAxisValue()));
        });
        listReadings();
    }
    
    @Override
    protected Component buildContent() {
        return new CssLayout(chart);
    }
    
    private void listReadings() {
        
        for (TemperatureReading reading : engineService.getDailyReadings()) {
            DataSeries target;
            switch (reading.getPlace()) {
                case POSTPROCESSING:
                    target = post;
                    break;
                case PREPROCESSING:
                    target = pre;
                    break;
                case ENGINE:
                default:
                    target = main;
            }
            
            target.add(new DataSeriesItem(reading.getDate(), reading.getTemperature()));
        }
    }
    
}
