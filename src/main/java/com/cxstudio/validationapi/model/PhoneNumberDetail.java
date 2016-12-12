package com.cxstudio.validationapi.model;

/**
 * Plain POJO utilize to phone number detail transfer across layer.
 * 
 * @author Mohamed
 *
 */
public class PhoneNumberDetail {
	
	private String phoneNumber;
	
	private String isoCountryCode;
	
	private String geoLocation;
	
	private boolean valid;
	
	public PhoneNumberDetail(){
		
	}
	
	public PhoneNumberDetail(String phoneNumber, String isoCountryCode){
		this.phoneNumber = phoneNumber;
		this.isoCountryCode = isoCountryCode;
	}

	public PhoneNumberDetail(String phoneNumber, String isoCountryCode, boolean valid, String geolocation){
		this.phoneNumber = phoneNumber;
		this.isoCountryCode = isoCountryCode;
		this.valid = valid;
		this.geoLocation = geolocation;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	public String getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}	
}
