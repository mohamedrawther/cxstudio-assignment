package com.cxstudio.validationapi.endpoint.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import com.cxstudio.validationapi.model.PhoneNumberDetail;
import com.cxstudio.validationapi.validation.PhoneNumberValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.mockito.BDDMockito.*;
import static com.cxstudio.validationapi.util.PhoneNumberToJsonConversionFunction.phoneNumberObjToJson;

/**
 * Verifying ContactDetailsController functionality is working properly. Spring
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
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPhoneNumberValidationRequestFailedWhenBadURL() throws Exception {
		given(phoneNumberValidator.validate(anyString(), anyString())).willReturn(false);
		this.mvc.perform(get("/validate?phoneNumber=44716390&isoCountryCode=NZ").accept(APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isNotFound());
	}
		
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPhoneNumberValidationResponseContentWhenCorrectURLAndRequestParam() throws Exception {
		PhoneNumberDetail expectedPhoneNumberValidation = new PhoneNumberDetail("44716390", "NZ");
		expectedPhoneNumberValidation.setValid(false);
		given(phoneNumberValidator.validate(anyString(), anyString())).willReturn(false);
		this.mvc.perform(
				get("/phoneNumber/validate?phoneNumber=44716390&isoCountryCode=NZ").accept(APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(phoneNumberObjToJson.apply(expectedPhoneNumberValidation)));
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPhoneNumberValidationResponseWhenValidPhoneNumber() throws Exception {
		PhoneNumberDetail expectedPhoneNumberValidation = new PhoneNumberDetail("+52 1 55 4142 3370", "MX");
		expectedPhoneNumberValidation.setValid(true);
		expectedPhoneNumberValidation.setGeoLocation("");
		given(phoneNumberValidator.validate(anyString(), anyString())).willReturn(true);
		this.mvc.perform(
				get("/phoneNumber/validate?phoneNumber=5215541423370&isoCountryCode=MX").accept(APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(phoneNumberObjToJson.apply(expectedPhoneNumberValidation)));
	}

}
