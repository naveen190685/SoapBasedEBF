package com.SeleniumFramework.sauce.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * A util class to create a Joda Timestamps for UserNameToken Security Header 	values
 */

public class Central_Time_Stamp_Util {
	public static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	
	
	public static DateTime getCentralJoda() {
		DateTime utcTime = new DateTime(getCentralTimeDateTimeNow(),
				DateTimeZone.UTC);
		return utcTime;
	}

	public static DateTime getCentralJodaPlusFive() {
		DateTime utcTime = new DateTime(getCentralTimeDateTimeNowPlusFiveMin(),
				DateTimeZone.UTC);
		return utcTime;
	}	

	public static String getCentralTimeDateTimeNow() {
		return getCentralTimeFromCalender(Calendar.getInstance());
	}

	public static String getCentralTimeDateTimeNowPlusFiveMin() {
		return getCentralTimeDateTimeNowPlusFiveMin(Calendar
				.getInstance());
	}

	private static String getCentralTimeFromCalender(Calendar cal) {
		Date central_date_plus_five_minutes = cal.getTime();
		String formatDate = new SimpleDateFormat(ISO8601_DATE_FORMAT)
				.format(central_date_plus_five_minutes);
		return formatDate.substring(0, 22) + ":" + formatDate.substring(22);
	}

	private static String getCentralTimeDateTimeNowPlusFiveMin(Calendar cal) {
		cal.add(Calendar.MINUTE, 5);
		Date central_date_now = cal.getTime();
		String formatDate = new SimpleDateFormat(ISO8601_DATE_FORMAT)
				.format(central_date_now);
		return formatDate.substring(0, 22) + ":" + formatDate.substring(22);
	}

	public static XMLGregorianCalendar getXmlGregorianDateTimeNow() {
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xgcal = null;
		try {
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return xgcal;
	}

	public static void main(String[] args) {

	}

}
