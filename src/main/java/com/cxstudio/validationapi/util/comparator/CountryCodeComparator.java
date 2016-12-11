package com.cxstudio.validationapi.util.comparator;

import java.util.Arrays;
import java.util.Comparator;

public class CountryCodeComparator {
	
	private static String LANGUAGE_CODE_ENGLISH = "EN";
	private static String LANGUAGE_CODE_DUTCH = "NL";
	
	public static Comparator<String> getComparator(final String languageCode){
		
		return (String countryCodeDetailFrom, String countryCodeDetailTo) -> {
			// Default comparator
			int countryNameLocation = 2;
			if (languageCode.equalsIgnoreCase(LANGUAGE_CODE_DUTCH)){
				countryNameLocation = 1;
			}			
			String [] test = countryCodeDetailFrom.split("\\t");
			System.out.println("================" + test[0]);
			System.out.println("================" + test[1]);
			return Arrays.asList(countryCodeDetailFrom.split("\\t")).get(countryNameLocation)
					.compareTo(Arrays.asList(countryCodeDetailTo.split("\\t")).get(countryNameLocation));
		};		
	}
}
