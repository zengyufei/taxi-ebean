package com.ys.admin.base.converters;

import lombok.EqualsAndHashCode;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@EqualsAndHashCode
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

	private final static String REGEX_YMD = "\\d{4}-(([1-9])|(0[1-9])|(1[0-2]))-(([1-9])|(0[1-9])|([12]\\d)|(3[01]))";
	private final static String FORMAT_YMD = "yyyy-MM-dd";

	@Override
	public LocalDate convert(String s) {
		if (Pattern.compile(REGEX_YMD).matcher(s).matches()) {
			return LocalDate.parse(s,DateTimeFormatter.ofPattern(FORMAT_YMD));
		}
		return null;
	}


}
