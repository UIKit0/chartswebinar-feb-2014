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

package com.vaadin.chartwebinar;

import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.themes.GrayTheme;

public class MyCustomTheme extends GrayTheme {

    public MyCustomTheme() {
        getChart().setBorderRadius(0);
        getChart().setBackgroundColor(new SolidColor(60, 60, 60));
    }
    
}
