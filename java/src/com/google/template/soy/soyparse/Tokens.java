/*
 * Copyright 2015 Google Inc.
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

package com.google.template.soy.soyparse;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.template.soy.base.SourceLocation;
import com.google.template.soy.base.SourceLocation.Point;

/** Helpers for dealing with {@link Token tokens} */
final class Tokens {

  /**
   * Returns a source location for the given tokens.
   *
   * <p>All the provided tokens should be in strictly increasing order.
   */
  static SourceLocation createSrcLoc(String filePath, Token first, Token... rest) {
    int beginLine = first.beginLine;
    int beginColumn = first.beginColumn;
    int endLine = first.endLine;
    int endColumn = first.endColumn;

    for (Token next : rest) {
      checkArgument(startsLaterThan(next, beginLine, beginColumn));
      checkArgument(endsLaterThan(next, endLine, endColumn));
      endLine = next.endLine;
      endColumn = next.endColumn;
    }
    // this special case happens for completely empty files.
    if (beginLine == 0 && endLine == 0 && beginColumn == 0 && endColumn == 0) {
      return new SourceLocation(filePath);
    }
    return new SourceLocation(filePath, beginLine, beginColumn, endLine, endColumn);
  }

  static Point getPreviousPointOrBeginOfLine(Token token) {
    // TODO(tomnguyen) Change this to token.beginLine -1, EOL
    if (token.beginColumn == 1) {
      return Point.create(token.beginLine, token.beginColumn);
    }
    return Point.create(token.beginLine, token.beginColumn - 1);
  }

  private static boolean startsLaterThan(Token tok, int beginLine, int beginCol) {
    return tok.beginLine > beginLine || (tok.beginLine == beginLine && tok.beginColumn > beginCol);
  }

  private static boolean endsLaterThan(Token tok, int endLine, int endCol) {
    return tok.endLine > endLine || (tok.endLine == endLine && tok.endColumn > endCol);
  }

  /**
   * Returns {@code true} if the two tokens are adjacent in the input stream with no intervening
   * characters.
   */
  static boolean areAdjacent(Token first, Token second) {
    return first.endLine == second.beginLine && first.endColumn == second.beginColumn - 1;
  }

  private Tokens() {}
}
