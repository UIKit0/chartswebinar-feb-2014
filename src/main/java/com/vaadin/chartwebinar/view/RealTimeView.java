package com.vaadin.chartwebinar.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.chartwebinar.backend.EngineService;
import com.vaadin.chartwebinar.backend.TemperatureReading;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.maddon.layouts.MVerticalLayout;

@CDIView(value = RealTimeView.VIEW_ID)
public class RealTimeView extends AbstractView {

    public static final String VIEW_ID = "rt";

    @Inject
    EngineService service;

    Label main = new Label();
    Label pre = new Label();
    Label post = new Label();

    @PostConstruct
    void setup() {
        main.setCaption("@ engine");
        pre.setCaption("@ pre-processing");
        post.setCaption("@ post-processing");
    }

    @Override
    protected Component buildContent() {
        return new MVerticalLayout(main, pre, post);
    }

    public void onTemperatureReading(TemperatureReading reading) {
        final UI ui = getUI();
        if (ui != null) {
            final Label target;
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
                target.setValue(reading.getTemperature() + " (read " + reading.getDate() + ")");
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
