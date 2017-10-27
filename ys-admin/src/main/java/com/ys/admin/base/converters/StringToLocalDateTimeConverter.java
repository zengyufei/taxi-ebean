package com.ys.admin.base.converters;

import lombok.EqualsAndHashCode;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@EqualsAndHashCode
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

	private final static String REGEX_YMD = "\\d{4}-(([1-9])|(0[1-9])|(1[0-2]))-(([1-9])|(0[1-9])|([12]\\d)|(3[01]))";
	private final static String REGEX_YMDHMS = REGEX_YMD + "\\s(00|0|(0[0-9])|(1\\d)|(2[0-3])):(00|0|(0[0-9])|([1-5]\\d)):(00|0|(0[1-9])|([1-5]\\d))";
	private final static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	@Override
	public LocalDateTime convert(String s) {
		if (Pattern.compile(REGEX_YMDHMS).matcher(s).matches()) {
			return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(FORMAT_YMDHMS));
		}
		return null;
	}
}
