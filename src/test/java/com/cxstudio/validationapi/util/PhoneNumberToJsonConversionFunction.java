package com.cxstudio.validationapi.util;

import java.util.function.Function;

import com.cxstudio.validationapi.model.PhoneNumberDetail;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class to convert phoneNumber json string to phoneNumberDetail object
 * @author Mohamed
 *
 */
public class PhoneNumberToJsonConversionFunction {

	/**
	 * Function for converting json string to ContactDeatils object
	 */
	public static Function<PhoneNumberDetail, String> phoneNumberObjToJson = (PhoneNumberDetail phoneNumberDetail) -> {
		String phoneNumberDetailJson = null;
		try {
			phoneNumberDetailJson = new ObjectMapper().writeValueAsString(phoneNumberDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return phoneNumberDetailJson;
	};

}
