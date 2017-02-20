package com.yingjun.ssm.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by quanlie on 17/2/21.
 */
public class MyXssFormatter implements Formatter<String> {

	public String parse(String text, Locale locale) throws ParseException {
		return text == null ? null : StringEscapeUtils.escapeHtml4(text.trim());
	}

	public String print(String text, Locale locale) {
		return null;
	}
}