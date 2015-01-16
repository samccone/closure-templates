/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.template.soy.javasrc.restricted;

import com.google.template.soy.data.restricted.FloatData;
import com.google.template.soy.data.restricted.StringData;


/**
 * Utilities for implementing {@code SoyJavaSrcFunction}s.
 *
 * <p> Important: This class may only be used in implementing plugins (e.g. functions, directives).
 *
 * <p> Feel free to static import these helpers in your function implementation classes.
 *
 */
public class SoyJavaSrcFunctionUtils {

  private SoyJavaSrcFunctionUtils() {}


  /**
   * Creates a new JavaExpr with the given exprText and type FloatData.
   * @param exprText The Java expression text.
   */
  public static JavaExpr toFloatJavaExpr(String exprText) {
    return new JavaExpr(exprText, FloatData.class, Integer.MAX_VALUE);
  }


  /**
   * Creates a new JavaExpr with the given exprText and type StringData.
   * @param exprText The Java expression text.
   */
  public static JavaExpr toStringJavaExpr(String exprText) {
    return new JavaExpr(exprText, StringData.class, Integer.MAX_VALUE);
  }

}
