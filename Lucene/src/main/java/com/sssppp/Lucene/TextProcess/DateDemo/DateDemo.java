package com.sssppp.Lucene.TextProcess.DateDemo;

import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;

public class DateDemo {

	public static void main(String[] args) {
		/* x DateDemo.1 */
		Date now = new Date();
		String strMilli = DateTools.dateToString(now, Resolution.MILLISECOND);
		String strDay = DateTools.dateToString(now, Resolution.DAY);
		long numDay = DateTools.round(now.getTime(), Resolution.DAY);
		/* x */

		System.out.println("now: " + now.toString());
		System.out.println("strMilli: " + strMilli);
		System.out.println("strDay: " + strDay);
		System.out.println("time now: " + now.getTime());
		System.out.println("time rounded: " + numDay);

	}
}
/**
 * now: Mon Jul 25 11:37:54 CST 2016
 * strMilli: 20160725033754164
 * strDay: 20160725
 * time now: 1469417874164
 * time rounded: 1469404800000
 */
