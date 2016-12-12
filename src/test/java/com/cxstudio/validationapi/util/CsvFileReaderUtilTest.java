package com.cxstudio.validationapi.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Testing CsvFileReaderUtil class which is working properly
 * 
 * @author Mohamed
 *
 */
public class CsvFileReaderUtilTest {

	/**
	 * Testing country-codes.csv file reading with Charset ISO_8859_1
	 * 
	 */
	@Test
	public void testGetCountryCodeDetailsReadFromCsvFileWhenCorrectCSVFilePath(){
		assertThat(CsvFileReaderUtil.getCountryCodeDetails("country-codes.csv").size() > 0).isEqualTo(true);
	}
		
}
