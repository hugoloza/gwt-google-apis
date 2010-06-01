/*
 * Copyright 2010 Google Inc.
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
package com.google.gwt.gadgets.client.io;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A class defining request options used with {@link IoFeature#makeRequest}
 * methods.
 */
public class RequestOptions extends JavaScriptObject {

  /**
   * Returns new instance of {@link RequestOptions}.
   *
   * @return new instance of {@link RequestOptions}.
   */
  public static RequestOptions newInstance() {
    return JavaScriptObject.createObject().cast();
  }

  protected RequestOptions() {
  }

  /**
   * Sets a type of content of the requested resource. This influences the
   * method of parsing used for received data.
   *
   * @param contentType type of content of the requested resource.
   * @return this instance of {@link RequestOptions} for convenience in using
   *         the builder pattern.
   */
  public final native RequestOptions setContentType(ContentType contentType) /*-{
    this[$wnd.gadgets.io.RequestParameters.CONTENT_TYPE] = contentType.@com.google.gwt.gadgets.client.io.ContentType::getContentType()();
    return this;
  }-*/;

  /**
   * Sets the HTTP request method. Note that the only request method required by
   * container to implement is {@link MethodType#GET} method. Implementation of
   * remaining methods is optional.
   *
   * @param methodType the HTTP request method.
   * @return this instance of {@link RequestOptions} for convenience in using
   *         the builder pattern.
   */
  public final native RequestOptions setMethodType(MethodType methodType) /*-{
    this[$wnd.gadgets.io.RequestParameters.METHOD] = methodType.@com.google.gwt.gadgets.client.io.MethodType::getMethodType()();
    return this;
  }-*/;

  /**
   * Explicitly sets the lifespan of cached content. The Refresh Interval is the
   * number of seconds the container should cache the given response. By
   * default, the HTTP caching headers will be respected for fetched content. If
   * the refresh interval is set, this value will take precedence over any HTTP
   * cache headers. If this value is not set and there are no HTTP caching
   * headers specified, this value will default to 3600 (one hour). Note that
   * Signed requests and objects with POST_DATA present will generally not be
   * cached.
   *
   * @param interval the lifespan of cached content in seconds.
   * @return this instance of {@link RequestOptions} for convenience in using
   *         the builder pattern.
   */
  public final native RequestOptions setRefreshInterval(int interval) /*-{
    this[$wnd.gadgets.io.RequestParameters.REFRESH_INTERVAL] = interval;
    return this;
  }-*/;
}