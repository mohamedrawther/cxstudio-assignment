package com.cxstudio.validationapi.endpoint.rest;

import static com.cxstudio.validationapi.util.PhoneNumberToJsonConversionFunction.phoneNumberObjToJson;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cxstudio.validationapi.model.PhoneNumberDetail;
import com.cxstudio.validationapi.validation.PhoneNumberValidator;

/**
 * Verifying PhoneNumberController functionality is working properly. Spring
 * Test API used to test the controller functionality. The following are the
 * testing objectives. 1. Accepting HTTP GET/POST/etc request 2. Verify
 * ContentType APPLICATION_JSON_VALUE 3. Verify Response Status 4. Verify URL
 * mapping correctly
 * 
 * @author Mohamed
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PhoneNumberController.class)
public class PhoneNumberControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PhoneNumberValidator phoneNumberValidator;

	/**
	 * Testing invalid request by sending invalid URL
	 * @throws Exception
	 */
	@Test
	public void testPhoneNumberValidationRequestFailedWhenBadURL() throws Exception {
		given(phoneNumberValidator.validate(anyString(), anyString())).willReturn(false);
		this.mvc.perform(get("/validate?phoneNumber=44716390&isoCountryCode=NZ").accept(APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isNotFound());
	}
		
	/**
	 * Testing invalid phone number validation, validation is mocked and considered as invalid
	 * by mocking validator.validate return false.
	 * @throws Exception
	 */
	@Test
	public void testPhoneNumberValidationResponseContentWhenCorrectURLAndRequestParam() throws Exception {
		PhoneNumberDetail expectedPhoneNumberValidation = new PhoneNumberDetail("44716390", "NZ", false, null);
		given(phoneNumberValidator.validate(anyString(), anyString())).willReturn(false);
		this.mvc.perform(
				get("/phoneNumber/validate?phoneNumber=44716390&isoCountryCode=NZ").accept(APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(phoneNumberObjToJson.apply(expectedPhoneNumberValidation)));
	}
	
	/**
	 * Testing valid phone number validation, validation is mocked and considered as valid
	 * by mocking validator.validate return true.
	 * @throws Exception
	 */
	@Test
	public void testPhoneNumberValidationResponseWhenValidPhoneNumber() throws Exception {
		PhoneNumberDetail expectedPhoneNumberValidation = new PhoneNumberDetail("+52 1 55 4142 3370", "MX", true, "Mexico");
		given(phoneNumberValidator.validate(anyString(), anyString())).willReturn(true);
		this.mvc.perform(
				get("/phoneNumber/validate?phoneNumber=5215541423370&isoCountryCode=MX").accept(APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(phoneNumberObjToJson.apply(expectedPhoneNumberValidation)));
	}

}
