package com.cxstudio.validationapi.util.comparator;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
/**
 * Comparator for comparing two country code details.
 * The comparing string has country code, country name in dutch and 
 * country name in english. Tab space used to delimit the data.
 * @author Mohamed
 *
 */
public class CountryCodeComparator {
	
	private static String LANGUAGE_CODE_DUTCH = "NL";
	
	/**
	 * Comparing two country code details. Tab space used to delimit the country details
	 * Ex: MN	Mongolië	Mongolia
	 * @param languageCode
	 * @return
	 */
	public static Comparator<String> getComparator(final String languageCode){
		
		return (String countryCodeDetailFrom, String countryCodeDetailTo) -> {
			int countryNameLocation = 2;
			if (languageCode.equalsIgnoreCase(LANGUAGE_CODE_DUTCH)){
				countryNameLocation = 1;
			}
			String comparefrom = new String(Arrays.asList(countryCodeDetailFrom.split("\\t")).get(countryNameLocation).getBytes(), StandardCharsets.ISO_8859_1);
			String compareto = new String(Arrays.asList(countryCodeDetailTo.split("\\t")).get(countryNameLocation).getBytes(), StandardCharsets.ISO_8859_1);
			
			return comparefrom.compareTo(compareto);
		};		
	}
}
