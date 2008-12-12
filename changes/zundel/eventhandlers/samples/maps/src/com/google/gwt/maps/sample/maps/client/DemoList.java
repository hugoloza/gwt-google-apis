/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.maps.sample.maps.client;

import com.google.gwt.maps.sample.maps.client.MapsDemo.MapsDemoInfo;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;

/**
 * The left panel that contains all of the sinks, along with a short description
 * of each.
 */
public class DemoList extends Composite {

//  private HorizontalPanel list = new HorizontalPanel();
  private ListBox list = new ListBox();
  private ArrayList<MapsDemoInfo> sinks = new ArrayList<MapsDemoInfo>();

  public DemoList() {
    initWidget(list);
    list.setVisibleItemCount(1);
    list.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        History.newItem(list.getItemText(list.getSelectedIndex()));
      }      
    });
  }

  public void addMapsDemo(final MapsDemoInfo info) {
    String name = info.getName();
    list.addItem(name);
    sinks.add(info);
  }

  public MapsDemoInfo find(String sinkName) {
    for (int i = 0; i < sinks.size(); ++i) {
      MapsDemoInfo info = sinks.get(i);
      if (info.getName().equals(sinkName)) {
        return info;
      }
    }
    return null;
  }

  public void setMapsDemoSelection(String name) {
    for (int i = 0; i < list.getItemCount(); i++) {
      if (name.equals(list.getItemText(i))) {
        list.setSelectedIndex(i);
        break;
      }
    }
  }
}