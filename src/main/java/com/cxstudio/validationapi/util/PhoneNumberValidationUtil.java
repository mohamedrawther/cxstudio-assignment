package com.cxstudio.validationapi.util;

import static com.cxstudio.validationapi.util.CsvFileReaderUtil.getCountryCodeDetails;

import java.util.Arrays;
import java.util.Optional;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 * 
 * @author Mohamed
 *
 */
public class PhoneNumberValidationUtil {

	//
	public static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

	/**
	 * 
	 * @param phoneNumber
	 * @param countryCode
	 * @return
	 */
	public static boolean validatePhoneNumber(String phoneNumber, String countryCode) {
		Phonenumber.PhoneNumber phoneNumberProto = null;
		try {
			phoneNumberProto = phoneNumberUtil.parse(phoneNumber, countryCode);

		} catch (NumberParseException phoneNumberParsingException) {
			return false;
		}
		return phoneNumberUtil.isValidNumber(phoneNumberProto);
	}

	/**
	 * 
	 * @param phoneNumber
	 * @param countryCode
	 * @return
	 */
	public static String parsePhoneNumberInternationalFormat(String phoneNumber, String countryCode) {
		Phonenumber.PhoneNumber phoneNumberProto = null;
		StringBuilder internationalFormatPhoneNumber = new StringBuilder(20);
		try {
			phoneNumberProto = phoneNumberUtil.parse(phoneNumber, countryCode);

		} catch (NumberParseException phoneNumberParsingException) {
			return phoneNumber;
		}
		phoneNumberUtil.format(phoneNumberProto, PhoneNumberFormat.INTERNATIONAL, internationalFormatPhoneNumber);
		return internationalFormatPhoneNumber.toString();
	}

	/**
	 * 
	 * @param phoneNumber
	 * @param countryCode
	 * @return
	 */
	public static String findGeoLocation(String countryCode, String countryCodeCsvFileName){
		Optional<String> geoLocation = getCountryCodeDetails(countryCodeCsvFileName).stream().filter((String countryCodeDetails)->countryCodeDetails.substring(0,2).equalsIgnoreCase(countryCode)).findAny();		
		return Arrays.asList(geoLocation.get().split("\\t")).get(2);
	}

}
