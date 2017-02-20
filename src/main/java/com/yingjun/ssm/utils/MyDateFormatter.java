package com.yingjun.ssm.utils;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDateFormatter implements Formatter<Date>{

	public Date parse(String s, Locale locale) throws ParseException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormat.setLenient(false);
			return dateFormat.parse(s);
		} catch (ParseException e) {
			throw new IllegalArgumentException("invalid date format");
		}
	}

	public String print(Date date, Locale locale) {
		return null;
	}
}
