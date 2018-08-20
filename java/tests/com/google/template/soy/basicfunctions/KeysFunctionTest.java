/*
 * Copyright 2011 Google Inc.
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

package com.google.template.soy.basicfunctions;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.SoyValueConverterUtility;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.plugin.java.restricted.testing.SoyJavaSourceFunctionTester;
import com.google.template.soy.pysrc.restricted.PyExpr;
import com.google.template.soy.pysrc.restricted.PyListExpr;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for KeysFunction.
 *
 */
@RunWith(JUnit4.class)
public class KeysFunctionTest {

  @Test
  public void testComputeForJavaSource() {
    KeysFunction keysFunction = new KeysFunction();
    SoyJavaSourceFunctionTester tester = new SoyJavaSourceFunctionTester(keysFunction);

    SoyValue map =
        SoyValueConverterUtility.newDict(
            "boo", "bar", "foo", 2, "goo", SoyValueConverterUtility.newDict("moo", 4));
    @SuppressWarnings("unchecked")
    List<SoyValue> result = (List<SoyValue>) tester.callFunction(map);
    assertThat(result).containsExactly("boo", "foo", "goo");
  }

  @Test
  public void testComputeForJsSrc() {
    KeysFunction keysFunction = new KeysFunction();
    JsExpr expr = new JsExpr("JS_CODE", Integer.MAX_VALUE);
    assertThat(keysFunction.computeForJsSrc(ImmutableList.of(expr)))
        .isEqualTo(new JsExpr("soy.$$getMapKeys(JS_CODE)", Integer.MAX_VALUE));
  }

  @Test
  public void testComputeForPySrc() {
    KeysFunction keysFunction = new KeysFunction();
    PyExpr dict = new PyExpr("dictionary", Integer.MAX_VALUE);
    assertThat(keysFunction.computeForPySrc(ImmutableList.of(dict)))
        .isEqualTo(new PyListExpr("(dictionary).keys()", Integer.MAX_VALUE));
  }
}
