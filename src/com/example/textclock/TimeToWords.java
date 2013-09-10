package com.example.textclock;

import java.util.Calendar;

public class TimeToWords {

	// Fields -----------------------------------------------------------------
	public static final String[] UNITS = {
		"zero",
		"one",
		"two",
		"three",
		"four",
		"five",
		"six",
		"seven",
		"eight",
		"nine"
	};
	public static final String[] TENS = {
		"twenty",
		"thirty",
		"forty",
		"fifty",
		"sixty",
		"seventy",
		"eighty",
		"ninety"
	};
	public static final String[] TEENS = {
		"ten",
		"eleven",
		"twelve",
		"thirteen",
		"fourteen",
		"fifteen",
		"sixteen",
		"seventeen",
		"eighteen",
		"nineteen"
	};
	
	private static final StringBuilder builder = new StringBuilder();
	
	// Methods ----------------------------------------------------------------
	public synchronized static String[] timeToWords(Calendar date){
		
		// Initialize the builder
		builder.setLength(0);
		
		// Get the hour and minute
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int minute = date.get(Calendar.MINUTE);
		
		// Check if it is midnight
		if (hour == 0){
			builder.append("midnight");
		}else{
			toWords(hour, builder, false, "");
		}
		
		// Check if it is the beginning of a new hour
		if (minute == 0){
			if (hour > 0){
				builder.append(":o'clock");	
			}
		}else{
			builder.append(":");
			toWords(minute, builder, true, ":");
		}
		
		return builder.toString().split(":");
	}
	private static void toWords(
			final int number,
			StringBuilder builder,
			boolean leadingZero,
			String separator){
		int num = number;
		int tens = num / 10;
		if (leadingZero || tens > 0){
			if (tens == 1){
				builder.append(TEENS[num - 10]);
				num = 0;
			}else{
				builder.append(TENS[tens]);
			}
		}
		int units = num % 10;
		if (units > 0){
			if (builder.length() > 0){
				builder.append(separator);
			}
			builder.append(UNITS[units]);
		}
	}
}
