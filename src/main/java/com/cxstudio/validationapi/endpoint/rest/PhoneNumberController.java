package com.cxstudio.validationapi.endpoint.rest;

import static com.cxstudio.validationapi.util.CsvFileReaderUtil.getCountryCodeDetails;
import static com.cxstudio.validationapi.util.PhoneNumberValidationUtil.findGeoLocation;
import static com.cxstudio.validationapi.util.PhoneNumberValidationUtil.parsePhoneNumberInternationalFormat;
import static com.cxstudio.validationapi.util.comparator.CountryCodeComparator.getComparator;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxstudio.validationapi.model.PhoneNumberDetail;
import com.cxstudio.validationapi.validation.PhoneNumberValidator;

/**
 * This class acting as RestController to receive REST request with JSON body.
 * The request URL should be
 * http://{hostname:port/applicationContext}/phoneNumber. The request would be
 * handled based on HTTP Request Method(GET/POST/DELETE/etc)
 * 
 * PhoneNumberController handling phoneNumber validation request/response and
 * country code sorting based on country name in the language.
 * 
 * @author Mohamed
 *
 */
@RestController
@RequestMapping(value = "/phoneNumber", produces = APPLICATION_JSON_UTF8_VALUE)
public class PhoneNumberController {

	// Logger instance for logging request/response data
	private static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberController.class);

	@Autowired
	PhoneNumberValidator phoneNumberValidator;

	// country code csv file name from resource file
	@Value("${file.csv.countrycode}")
	String countryCodeCsvFileName;

	/**
	 * Handling GET request with URL
	 * http://{hostname:port/applicationContext}/contactdetails with JSON
	 * content This handler is validating phoneNumber and sending validation
	 * result as response
	 * 
	 * @param phoneNumber
	 *            has data to validate the phoneNumber
	 * @param isoCountryCode
	 *            has data to identifying country by code
	 * @return phoneNumberDetail has validation result and phone number details
	 */
	@GetMapping
	@RequestMapping(value = "/validate")
	public PhoneNumberDetail validatePhoneNumber(
			@RequestParam(name = "phoneNumber", required = true) String phoneNumber,
			@RequestParam(name = "isoCountryCode", required = true) String isoCountryCode) {

		LOGGER.info("validatePhoneNumber by phoneNumber, isoCountryCode " + phoneNumber + "; " + isoCountryCode);
		PhoneNumberDetail phoneNumberDetail = new PhoneNumberDetail(phoneNumber, isoCountryCode);

		// Validating phone number
		phoneNumberDetail.setValid(phoneNumberValidator.validate(phoneNumber, isoCountryCode));
		if (phoneNumberDetail.isValid()) {
			LOGGER.info("Validated successfully phoneNumber, isoCountryCode " + phoneNumber + "; " + isoCountryCode);
			// Parsing given phone number to international format and finding
			// geo location based on country code
			phoneNumberDetail.setPhoneNumber(parsePhoneNumberInternationalFormat(phoneNumber, isoCountryCode));
			phoneNumberDetail.setGeoLocation(findGeoLocation(isoCountryCode, countryCodeCsvFileName));
		}

		return phoneNumberDetail;
	}

	/**
	 * Handling GET request with URL
	 * http://{hostname:port/applicationContext}/countryCodes and
	 * countryLanguageCode as request parameter to identify sorting data
	 * 
	 * @param countryNameInLanguage
	 * @return
	 */
	@GetMapping
	@RequestMapping(value = "/countryCodes")
	public List<String> findAllCountryCodes(@RequestParam(name = "countryLanguageCode") String countryLanguageCode) {
		LOGGER.info("findAllCountryCodes by countryLanguageCode " + countryLanguageCode);
		// sorting country code by countryName based on countryLanaguageCode
		return getCountryCodeDetails(countryCodeCsvFileName).stream().filter((String countryCode) -> {
			return !countryCode.startsWith("CountryCode");
		}).sorted(getComparator(countryLanguageCode)).collect(Collectors.toList());
	}
}
