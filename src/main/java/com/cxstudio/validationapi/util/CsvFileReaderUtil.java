package com.cxstudio.validationapi.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.util.ResourceUtils;

/**
 * This Utility class used to read file and serve countryCodeDetails list to
 * application
 * 
 * @author Mohamed
 *
 */
public class CsvFileReaderUtil {

	// CountryCode Details which is read from csv file
	private static Optional<List<String>> countryCodeDetails = Optional.ofNullable(null);

	/**
	 * Reading csv file from resource folder which is ISO_8859_1 format and
	 * loading into countryCodeDetails list
	 * 
	 * @param csvfilePath
	 *            csv file to read
	 */
	private static void readCsvFile(String csvfileName) {
		try {
			File file = ResourceUtils.getFile("classpath:" + csvfileName);
			countryCodeDetails = Optional.of(Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Get list of country code details which is read from csv file. Lazy file
	 * reading used to read file only once to serve the application.
	 * 
	 * @param csvfileName
	 *            file to read
	 * @return
	 */
	public static List<String> getCountryCodeDetails(String csvfileName) {
		if (!countryCodeDetails.isPresent())
			readCsvFile(csvfileName);
		return countryCodeDetails.get();
	}
}
