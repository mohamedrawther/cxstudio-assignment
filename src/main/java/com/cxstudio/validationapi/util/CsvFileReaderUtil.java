package com.cxstudio.validationapi.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.util.ResourceUtils;

public class CsvFileReaderUtil {

	private static Optional<List<String>> countryCodeDetails = Optional.ofNullable(null);
	
	private static void readCsvFile(String csvfilePath){
		try{
			File file = ResourceUtils.getFile("classpath:"+csvfilePath);
			countryCodeDetails = Optional.of(Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1));
			//System.out.println(countryCodeDetails.toString());
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	
	}
	
	public static List<String> getCountryCodeDetails(String csvfilePath){
		if(!countryCodeDetails.isPresent())
			readCsvFile(csvfilePath);
		return countryCodeDetails.get();
	}
}
