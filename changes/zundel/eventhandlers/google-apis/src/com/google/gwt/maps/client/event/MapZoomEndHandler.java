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
package com.google.gwt.maps.client.event;

import com.google.gwt.maps.client.MapWidget;

import java.util.EventObject;

/**
 * Provides an interface to implement in order to receive MapEvent.ZOOMENDevents
 * from the {@link MapWidget}.
 */
public interface MapZoomEndHandler {

  /**
   * Encapsulates the arguments for the MapEvent.ZOOMEND event on a
   * {@link MapWidget}.
   */
  class MapZoomEndEvent extends EventObject {
    final int newZoomLevel;
    final int oldZoomLevel;

    public MapZoomEndEvent(MapWidget source, int oldZoomLevel, int newZoomLevel) {
      super(source);
      this.oldZoomLevel = oldZoomLevel;
      this.newZoomLevel = newZoomLevel;
    }

    /**
     * Returns the zoom level after the event was fired.
     * 
     * @return the zoom level after the event was fired.
     */
    public int getNewZoomLevel() {
      return newZoomLevel;
    }

    /**
     * Returns the zoom level before the event was fired.
     * 
     * @return the zoom level before the event was fired.
     */
    public int getOldZoomLevel() {
      return oldZoomLevel;
    }

    /**
     * Returns the instance of the map that generated this event.
     * 
     * @return the instance of the map that generated this event.
     */
    public MapWidget getSender() {
      return (MapWidget) getSource();
    }
  }

  /**
   * Method to be invoked when a MapEvent.ZOOMEND event fires on a
   * {@link MapWidget}.
   * 
   * @param event contains the properties of the event.
   */
  void onZoomEnd(MapZoomEndEvent event);
}
