package org.aksw.gwt.TripleCheckMate.shared.storage;


// copied/adapted from org.apache.commons.lang3.StringEscapeUtils

public class CSVHelper {
	private static final String CSV_DELIMITER = ",";
	private static final String CSV_QUOTE = "\"";
	private static final String LF = "\n";
	private static final String CR = "\r";
	
	public static String escapeColumn(String input){

		if(input == null || input.length() == 0) {
			return "";
		}
		Boolean contCSVDel = input.contains(CSV_DELIMITER);
		Boolean contCSVQuote = input.contains(CSV_QUOTE);
		Boolean contLF = input.contains(LF);
		Boolean contCR = input.contains(CR);
		if (! (contCSVDel || contCSVQuote || contLF || contCR)) {
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
