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
package com.google.gwt.search.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.search.client.impl.GSearchControl;
import com.google.gwt.search.client.impl.GsearcherOptions;
import com.google.gwt.search.client.impl.KeepCallback;
import com.google.gwt.search.client.impl.SearchControlCompleteCallback;
import com.google.gwt.search.client.impl.SearchStartingCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Encapsulates the Google Ajax Search API search control.
 */
public class SearchControl extends Composite {
  private static final GSearchControl SEARCH_CONTROL = GWT.create(GSearchControl.class);

  /**
   * Enables the SearchControl facade to be used as a return type through JSIO
   * interfaces.
   * 
   * @throws RuntimeException because this should never be called.
   */
  static SearchControl createPeer(JavaScriptObject obj) {
    /*
     * The reason that this is as failure is because it would only ever be
     * invoked if a GSearchControl-JSO were being returned from a JSIO method
     * without having been previously bound to a SearchControl facade. Because
     * we do not expose the GSearchControl API, there should never be a instance
     * where an unbound GSearchControl is passed through this API.
     */
    throw new RuntimeException("SearchControl.createPeer() unimplemented.");
  }

  /**
   * The backing JSO, named per JSIO spec.
   */
  private final JavaScriptObject jsoPeer = SEARCH_CONTROL.construct();

  /**
   * Retains all KeepHandlers that should be notified when the user clicks on
   * the "keep" link in the SearchControl.
   */
  private final KeepHandlerCollection keepHandlers = new KeepHandlerCollection();

  /**
   * Retains all SearchHandlers that should be notified when the SearchControl
   * receives Result.
   */
  private final SearchHandlerCollection resultHandlers = new SearchHandlerCollection();

  /**
   * Retains all SearchStartingHandlers that should be notified when the search
   * begins.
   */
  private final SearchStartingHandlerCollection startingHandlers = new SearchStartingHandlerCollection();

  /**
   * Constructs a new SearchControl.
   * 
   * @param options Provides a configuration for displaying and executing the
   *          SearchControl's searches
   */
  public SearchControl(SearchControlOptions options) {
    SimplePanel contents = new SimplePanel();
    initWidget(contents);
    setStyleName("gwt-SearchControl");

    // Associate the backing JSO with this facade object.
    SEARCH_CONTROL.bind(jsoPeer, this);

    // Wire up the search callback every time
    SEARCH_CONTROL.setSearchCompleteCallback(this, null,
        new SearchControlCompleteCallback() {
          @Override
          public void onSearchResult(SearchControl control, Search search) {
            assert control == SearchControl.this;
            for (Result result : search.getResults()) {
              resultHandlers.fireResult(search, result);
            }
          }
        });

    // Wire up the search starting callback every time
    SEARCH_CONTROL.setSearchStartingCallback(this, null,
        new SearchStartingCallback() {
          @Override
          public void onSearchStart(SearchControl control, Search search,
              String query) {
            assert control == SearchControl.this;
            startingHandlers.fireResult(control, search, query);
          }
        });

    // If no keep label is explicitly set, don't bother wiring up the callback.
    if (options.keepLabel != null) {
      KeepCallback keepCallback = new KeepCallback() {
        @Override
        public void onKeep(Result result) {
          keepHandlers.fireKeep(SearchControl.this, result);
        }
      };

      // keepLabel may be either a String or a KeepLabel
      if (options.keepLabel instanceof String) {
        SEARCH_CONTROL.setOnKeepCallback(this, null, keepCallback,
            (String) options.keepLabel);
      } else if (options.keepLabel instanceof KeepLabel) {
        SEARCH_CONTROL.setOnKeepCallback(this, null, keepCallback,
            ((KeepLabel) options.keepLabel).getValue());
      }
    }

    // Explicitly set the linkTarget if one is defined.
    if (options.linkTarget != null) {
      SEARCH_CONTROL.setLinkTarget(this, options.linkTarget.getValue());
    }

    // Set the timeoutInterval if necessary
    if (options.timeoutInterval != null) {
      SEARCH_CONTROL.setTimeoutInterval(this,
          options.timeoutInterval.getValue());
    }

    // Add all Searches to the control
    for (Search search : options.searchers) {
      GsearcherOptions searchOptions = options.searcherOptions.get(search);
      SEARCH_CONTROL.addSearcher(this, search, searchOptions);
    }

    // Build the UI.
    SEARCH_CONTROL.draw(this, contents.getElement(), options.drawOptions);
  }

  /**
   * Adds a {@link KeepHandler} to the {@link SearchControl}. It is necessary
   * to have set a keep label by invoking
   * {@link SearchControlOptions#setKeepLabel(java.lang.String)} in order to
   * display the keep link on the SearchControl.
   * 
   * @param handler A {@link KeepHandler} that will receive notifications when
   *          the user clicks on a keep link in the {@link SearchControl}
   */
  public void addKeepHandler(KeepHandler handler) {
    keepHandlers.add(handler);
  }

  /**
   * Adds a {@link SearchHandler} to receive notification of all search results
   * loaded by the SearchControl. A SearchHandler added to the SearchControl
   * will receive Result objects from all Search objects added to the
   * SearchControl.
   * 
   * @param handler A {@link SearchHandler} that will receive notifications when
   *          the SearchControl has received a Result.
   */
  public void addSearchHandler(SearchHandler handler) {
    resultHandlers.add(handler);
  }

  /**
   * Adds a {@link SearchStartingHandler} to inform the search control that the
   * caller would like to be notified when a search starts.
   * 
   * @param handler a {@link SearchStartingHandler} that will receive
   *          notifications when the user invokes a search.
   */
  public void addSearchStartingHandler(SearchStartingHandler handler) {
    startingHandlers.add(handler);
  }

  /**
   * Programmatically execute a query. This allows the control to be seeded with
   * an initial query and search results. Using this method is equivalent to
   * typing a query into the SearchControl and pressing return.
   * 
   * @param query A search query
   */
  public void execute(String query) {
    SEARCH_CONTROL.execute(this, query);
  }

  /**
   * Removes a KeepHandler from the SearchControl. Removing all KeepHandlers
   * will not remove the keep links from the displayed SearchControl.
   * 
   * @param handler The KeepHandler to remove
   */
  public void removeKeepHandler(KeepHandler handler) {
    if (keepHandlers != null) {
      keepHandlers.remove(handler);
    }
  }

  /**
   * Removes a SearchHandler from the SearchControl.
   * 
   * @param handler The SearchHandler to remove
   */
  public void removeSearchHandler(SearchHandler handler) {
    if (resultHandlers != null) {
      resultHandlers.remove(handler);
    }
  }

  /**
   * Removes a SearchStaringHandler from the SearchControl.
   * 
   * @param handler The SearchStartingHandler to remove
   */
  public void removeSearchStartingHandler(SearchStartingHandler handler) {
    if (startingHandlers != null) {
      startingHandlers.remove(handler);
    }
  }
}