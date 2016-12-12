package com.cxstudio.validationapi.validation;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * Testing PhoneNumberValidator is working properly.
 * 
 * @author Mohamed
 *
 */
public class PhoneNumberValidatorTest {

	PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
	
	/**
	 * PhoneNumber validation success test with correct phone number and country code
	 * by calling PhoneNumberValidator
	 */
	@Test
	public void testPhonNumberValidationWhenValidPhoneNumberWithCountryCode(){
		assertThat(phoneNumberValidator.validate("5215541423370", "MX")).isEqualTo(true);
	}
	
	/**
	 * PhoneNumber validation failure test with invalid phone number and country code
	 * by calling PhoneNumberValidator
	 */
	@Test
	public void testPhonNumberValidationFailedWhenInvalidPhoneNumberWithCountryCode(){
		assertThat(phoneNumberValidator.validate("521554142370", "MX")).isEqualTo(false);
	}
	
	/**
	 * PhoneNumber validation failure test with correct phone number and invalid country code
	 * by calling PhoneNumberValidator
	 */
	@Test
	public void testPhonNumberValidationFailedWhenPhoneNumberWithInvalidsCountryCode(){
		assertThat(phoneNumberValidator.validate("5215541423370", "NZ")).isEqualTo(false);
	}
}
