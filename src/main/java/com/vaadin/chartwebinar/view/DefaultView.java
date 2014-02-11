package com.vaadin.chartwebinar.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.chartwebinar.backend.EngineService;
import com.vaadin.chartwebinar.backend.TemperatureReading;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.maddon.layouts.MVerticalLayout;

@CDIView
public class DefaultView extends AbstractView {

    VerticalLayout main = new MVerticalLayout();
    VerticalLayout pre = new MVerticalLayout();
    VerticalLayout post = new MVerticalLayout();
    VerticalLayout layout = new MVerticalLayout(main, pre, post);

    @Inject
    private EngineService engineService;
    
    @PostConstruct
    void setup() {
        layout.setCaption("All temperatures readings for last week:");
        main.setCaption("@ Engine");
        pre.setCaption("@ Pre processing");
        post.setCaption("@ Post processing");
        listReadings();
    }

    @Override
    protected Component buildContent() {
        return new CssLayout(layout);
    }

    private void listReadings() {

        for (TemperatureReading reading : engineService.getDailyReadings()) {
            Label label = new Label(reading.toString());
            VerticalLayout target;
            switch(reading.getPlace()) {
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

            target.addComponent(label);
        }
    }

}
