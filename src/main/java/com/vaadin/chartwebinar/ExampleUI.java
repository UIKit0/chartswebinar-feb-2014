package com.vaadin.chartwebinar;

import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.vaadin.maddon.layouts.MVerticalLayout;

@CDIUI
@Widgetset("com.vaadin.chartwebinar.AppWidgetSet")
public class ExampleUI extends UI {

    @Inject
    private CDIViewProvider viewProvider;

    @Override
    public void init(VaadinRequest request) {
        
        setPollInterval(500);
        ChartOptions.get().setTheme(new MyCustomTheme());
        
        VerticalLayout navigatorLayout = new MVerticalLayout().withFullHeight();

        Navigator navigator = new Navigator(ExampleUI.this, navigatorLayout);
        navigator.addProvider(viewProvider);

        setContent(navigatorLayout);
    }

}
