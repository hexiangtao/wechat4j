package com.qiluomite.mywechat.tuling123;

public class Location {

	private String city; // 例如:北京
	private String latitude;
	private String longitude;
	private String nearest_poi_name;// 例如:上地环岛南
	private String province;// 例如:北京
	private String street; // 例如:信息路

	public String getCity() {
		return city;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getNearest_poi_name() {
		return nearest_poi_name;
	}

	public String getProvince() {
		return province;
	}

	public String getStreet() {
		return street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setNearest_poi_name(String nearest_poi_name) {
		this.nearest_poi_name = nearest_poi_name;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setStreet(String street) {
		this.street = street;
	}

}
