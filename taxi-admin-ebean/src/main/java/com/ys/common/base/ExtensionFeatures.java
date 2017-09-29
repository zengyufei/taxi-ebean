package com.ys.common.base;

public interface ExtensionFeatures {
	
	String getFeatures();

	void setFeatures(String features);

	void setupFeature(String columnName, String value);

	void setupFeature(String columnName, Object value);

	void removeFeature(String columnName);

	String getFeature(String columnName);

	<T> T getFeature(String columnName, Class<T> clz);
}
