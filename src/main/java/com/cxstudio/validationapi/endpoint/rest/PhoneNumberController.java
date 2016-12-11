package com.cxstudio.validationapi.endpoint.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxstudio.validationapi.model.CountryCodeDetail;
import com.cxstudio.validationapi.model.PhoneNumberDetail;
import com.cxstudio.validationapi.util.PhoneNumberValidationUtil;
import com.cxstudio.validationapi.util.comparator.CountryCodeComparator;
import com.cxstudio.validationapi.validation.PhoneNumberValidator;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static com.cxstudio.validationapi.util.CsvFileReaderUtil.getCountryCodeDetails;
import static com.cxstudio.validationapi.util.comparator.CountryCodeComparator.getComparator;

import java.io.File;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This class acting as RestController to receive REST request with JSON body.
 * The request URL should be
 * http://{hostname:port/applicationContext}/contactdetails. The request would
 * be handled based on HTTP Request Method(GET/POST/DELETE/etc)
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

	@Value("${file.csv.countrycode}")
	String countryCodeCsvFileName;

	/**
	 * Handling GET request with URL
	 * http://{hostname:port/applicationContext}/contactdetails with JSON
	 * content This handler is validating phoneNumber and sending validation
	 * result as response
	 * 
	 * @param contactDetail
	 *            has data to validate the phoneNumber
	 * @return contactDetail which has validation result
	 */
	@GetMapping
	@RequestMapping(value = "/validate")
	public PhoneNumberDetail validatePhoneNumber(
			@RequestParam(name = "phoneNumber", required = true) String phoneNumber,
			@RequestParam(name = "isoCountryCode", required = true) String isoCountryCode) {

		PhoneNumberDetail phoneNumberDetail = new PhoneNumberDetail(phoneNumber, isoCountryCode);

		phoneNumberDetail.setValid(phoneNumberValidator.validate(phoneNumber, isoCountryCode));
		if (phoneNumberDetail.isValid()) {
			phoneNumberDetail.setPhoneNumber(
					PhoneNumberValidationUtil.parsePhoneNumberInternationalFormat(phoneNumber, isoCountryCode));
			phoneNumberDetail.setGeoLocation("");
		}

		return phoneNumberDetail;
	}

	/**
	 * 
	 * @param countryNameInLanguage
	 * @return
	 */
	@GetMapping
	@RequestMapping(value = "/countryCodes")
	public List<String> findAllCountryCodes(@RequestParam(name = "countryLanguageCode") String countryLanguageCode) {
		//getCountryCodeDetails(countryCodeCsvFileName).forEach((String s) -> System.out.println(s));
		return getCountryCodeDetails(countryCodeCsvFileName).stream().sorted(getComparator(countryLanguageCode))
				.collect(Collectors.toList());		
	}
}
