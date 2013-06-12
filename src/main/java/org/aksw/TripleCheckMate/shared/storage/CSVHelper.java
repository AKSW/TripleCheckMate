/*******************************************************************************
 * Copyright 2013 Agile Knowledge Engineering and Semantic Web (AKSW) Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.aksw.TripleCheckMate.shared.storage;


// copied/adapted from org.apache.commons.lang3.StringEscapeUtils

public class CSVHelper {
    private static final String CSV_DELIMITER = ",";
    private static final String CSV_QUOTE = "\"";
    private static final String LF = "\n";
    private static final String CR = "\r";

    public static String escapeColumn(String input) {

        if (input == null || input.length() == 0) {
            return "";
        }
        Boolean contCSVDel = input.contains(CSV_DELIMITER);
        Boolean contCSVQuote = input.contains(CSV_QUOTE);
        Boolean contLF = input.contains(LF);
        Boolean contCR = input.contains(CR);
        if (!(contCSVDel || contCSVQuote || contLF || contCR)) {
            return input;
        } else {
            String retVal = "";
            retVal += CSV_QUOTE;
            if (contCSVQuote)
                retVal += input.replace(CSV_QUOTE, CSV_QUOTE + CSV_QUOTE);
            else
                retVal += input;
            retVal += CSV_QUOTE;
            return retVal;
        }

    }


}
