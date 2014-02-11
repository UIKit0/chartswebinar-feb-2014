package com.vaadin.chartwebinar.view;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.cdi.CDIView;
import com.vaadin.chartwebinar.backend.EngineService;
import com.vaadin.chartwebinar.backend.TemperatureReading;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.maddon.layouts.MVerticalLayout;

@CDIView(value = RealTimeView.VIEW_ID)
public class RealTimeView extends AbstractView {

    public static final String VIEW_ID = "rt";

    @Inject
    EngineService service;

    DataSeries main = new DataSeries();
    DataSeries pre = new DataSeries();
    DataSeries post = new DataSeries();
    Chart chart = new Chart(ChartType.SPLINE);

    @PostConstruct
    void setup() {
        chart.setCaption("All temperatures readings for last week:");
        main.setName("@ Engine");
        pre.setName("@ Pre processing");
        post.setName("@ Post processing");
        chart.getConfiguration().setSeries(main, pre, post);
        chart.getConfiguration().getxAxis().setType(AxisType.DATETIME);
    }

    @Override
    protected Component buildContent() {
        return new MVerticalLayout(chart);
    }

    public void onTemperatureReading(TemperatureReading reading) {
        final UI ui = getUI();
        if (ui != null) {
            final DataSeries target;
            switch (reading.getPlace()) {
                case PREPROCESSING:
                    target = pre;
                    break;
                case POSTPROCESSING:
                    target = post;
                    break;
                default:
                    target = main;
            }
            ui.access(() -> {
                boolean shift = target.size() > 4;
                target.add(new DataSeriesItem(reading.getDate(), reading.getTemperature()), true, shift);
            });
        }
    }

    @Override
    public void attach() {
        super.attach();
        service.addRealTimeObserver(this::onTemperatureReading);
    }

    @Override
    public void detach() {
        super.detach();
        service.removeRealTimeObserver(this::onTemperatureReading);
    }

}
