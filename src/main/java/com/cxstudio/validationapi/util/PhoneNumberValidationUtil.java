package com.cxstudio.validationapi.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneNumberValidationUtil {

	private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
	
	public static boolean validatePhoneNumber(String phoneNumber, String countryCode){
		Phonenumber.PhoneNumber phoneNumberProto = null;
		try{
			phoneNumberProto = phoneNumberUtil.parse(phoneNumber, countryCode);
			
		}catch(NumberParseException phoneNumberParsingException){
			return false;
		}
		return phoneNumberUtil.isValidNumber(phoneNumberProto);
	}
	
	public static String parsePhoneNumberInternationalFormat(String phoneNumber, String countryCode){
		Phonenumber.PhoneNumber phoneNumberProto = null;
		StringBuilder internationalFormatPhoneNumber = new StringBuilder(20);
		try{
			phoneNumberProto = phoneNumberUtil.parse(phoneNumber, countryCode);
			
		}catch(NumberParseException phoneNumberParsingException){
			return phoneNumber;
		}
		phoneNumberUtil.format(phoneNumberProto, PhoneNumberFormat.INTERNATIONAL, internationalFormatPhoneNumber);
		return internationalFormatPhoneNumber.toString();
	}
		
}
