package com.cxstudio.validationapi.validation;

import static com.cxstudio.validationapi.util.PhoneNumberValidationUtil.validatePhoneNumber;

import org.springframework.stereotype.Component;
/**
 * This class validating phone number.
 * 
 * @author Mohamed
 *
 */
@Component
public class PhoneNumberValidator {

	/**
	 * validating phone number which is compliance, one of the mobile number
	 * or phone number
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public boolean validate(String phoneNumber, String countryCode) {
		return validatePhoneNumber(phoneNumber, countryCode);
	}
	
}
