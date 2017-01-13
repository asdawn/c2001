package net.c2001.utils;

import java.io.File;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Some common operations, including but not limited to:<br>
 * data transformation, console print of string array, MD5 calculation, call the
 * system commands, and get system environment variables. <br>
 * Note: this is a silent class, that is, it gives no text outputs.
 * 
 * @author Lin Dong
 */
public class CommonOps {
	/**
	 * No instance of this class is needed.
	 */
	private CommonOps() {

	}

	/**
	 * Gets the system property indicated by the specified key. {@link System}
	 * {@code .getProperty()}.
	 * 
	 * @param name
	 *            the name of the system property.
	 * @return the system property indicated by the specified key. <br>
	 *         Note: {@code getEnv} and {@code getProperty} are confusing, so I
	 *         create this method for convenience. You may directly use the
	 *         corresponding method provided by {@link java.lang.System}.
	 * 
	 */
	public static String getJavaProperty(String name) {
		return System.getProperty(name);
	}

	/**
	 * Open the given link with the default explorer.
	 * 
	 * @param url
	 *            URL to open.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public static boolean browseUrl(String url) {
		try {
			java.net.URI uri = new java.net.URI(url);
			java.awt.Desktop.getDesktop().browse(uri);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * List sub-directories of the given directory, only one level.
	 * 
	 * @param path
	 *            directory to search.
	 * @return sub-directories in {@link java.util.Vector}, If there's no
	 *         sub-directory the size would be 0. Return {@code null} if
	 *         {@code path} do not exist, or it is not a directory.
	 */
	public static Vector<File> getSubDirectory(String path) {
		File f = new File(path);
		Vector<File> dirs = new Vector<File>();
		if (f.exists() && f.isDirectory()) {
			File[] fdirs = f.listFiles();
			for (int i = 0; i < fdirs.length; i++) {
				if (fdirs[i].isDirectory())
					dirs.add(fdirs[i]);
			}
			return dirs;
		} else
			return null;
	}

	/**
	 * List file names ends with given string in the given directory.
	 * Sub-directory is not considered.
	 * 
	 * @param path
	 *            path of the directory to search.
	 * @param filterStr
	 *            file name filter, names that ends with the given string will
	 *            be returned.
	 * @return paths of files on success, {@code null} if the directory do not
	 *         exist or there's no such file.
	 * 
	 */
	public static List<String> getNamesInFolder(String path, String filterStr) {
		List<String> names = new ArrayList<String>();
		File files = new File(path);
		if (files.exists() == false) {
			return null;
		}
		if (files.isDirectory() == false) {
			return null;
		}

		File[] allFiles = files.listFiles();
		int i, len;
		String temp;
		for (i = 0; i < allFiles.length; i++) {
			temp = allFiles[i].getName();
			len = temp.length();
			if (temp.subSequence(len - filterStr.length(), len).equals(
					filterStr)) {
				names.add(temp);
			}
		}
		if (names.size() == 0)
			return null;
		else
			return names;
	}

	/**
	 * Get system properties.
	 * 
	 * @param prop
	 *            name of the property.
	 * @return value of the property, if there's no such property {@code null}
	 *         will be returned.
	 */
	public static String getSystemProperty(String prop) {
		try {
			String propStr = System.getenv(prop);
			return propStr;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Determine if a year is a leap year.
	 * 
	 * @param year
	 *            year (AD).
	 * @return {@code true} when year is a leap year, {@code false} otherwise.
	 */
	public static boolean isLeapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0)
			return true;
		else if (year % 400 == 0)
			return true;
		else
			return false;
	}

	/**
	 * Get the year number from given date.
	 * 
	 * @param date
	 *            date of yyyyMMdd format, for example, 19990101.
	 * 
	 * @return year number in the given date.
	 */
	public static int getYear(int date) {
		return date / 10000;
	}

	/**
	 * Get the month number from given date.
	 * 
	 * @param date
	 *            date of yyyyMMdd format, for example, 19990101.
	 * @return month number in the given date.
	 */
	public static int getMonth(int date) {
		return (date % 10000) / 100;
	}

	/**
	 * Get the day number from given date.
	 * 
	 * @param date
	 *            date of yyyyMMdd format, for example, 19990101.
	 * @return day number in the given date.
	 */
	public static int getDay(int date) {
		return date % 100;
	}

	/**
	 * Get the {@code day}th day in year {@code year}.
	 * 
	 * @param year
	 *            year number.
	 * @param day
	 *            day(from 1 to 365 or 366). Throws
	 *            {@code InvalidParameterException} when invalid parameter is
	 *            given.
	 * @return Corresponding date in format yyyyMMdd.
	 */
	public static int dateConvert(int year, int day) {
		final int[] nonLeanYear = { 101, 102, 103, 104, 105, 106, 107, 108,
				109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120,
				121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 201,
				202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213,
				214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225,
				226, 227, 228, 301, 302, 303, 304, 305, 306, 307, 308, 309,
				310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321,
				322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 401, 402,
				403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414,
				415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426,
				427, 428, 429, 430, 501, 502, 503, 504, 505, 506, 507, 508,
				509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520,
				521, 522, 523, 524, 525, 526, 527, 528, 529, 530, 531, 601,
				602, 603, 604, 605, 606, 607, 608, 609, 610, 611, 612, 613,
				614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625,
				626, 627, 628, 629, 630, 701, 702, 703, 704, 705, 706, 707,
				708, 709, 710, 711, 712, 713, 714, 715, 716, 717, 718, 719,
				720, 721, 722, 723, 724, 725, 726, 727, 728, 729, 730, 731,
				801, 802, 803, 804, 805, 806, 807, 808, 809, 810, 811, 812,
				813, 814, 815, 816, 817, 818, 819, 820, 821, 822, 823, 824,
				825, 826, 827, 828, 829, 830, 831, 901, 902, 903, 904, 905,
				906, 907, 908, 909, 910, 911, 912, 913, 914, 915, 916, 917,
				918, 919, 920, 921, 922, 923, 924, 925, 926, 927, 928, 929,
				930, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009,
				1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019,
				1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029,
				1030, 1031, 1101, 1102, 1103, 1104, 1105, 1106, 1107, 1108,
				1109, 1110, 1111, 1112, 1113, 1114, 1115, 1116, 1117, 1118,
				1119, 1120, 1121, 1122, 1123, 1124, 1125, 1126, 1127, 1128,
				1129, 1130, 1201, 1202, 1203, 1204, 1205, 1206, 1207, 1208,
				1209, 1210, 1211, 1212, 1213, 1214, 1215, 1216, 1217, 1218,
				1219, 1220, 1221, 1222, 1223, 1224, 1225, 1226, 1227, 1228,
				1229, 1230, 1231 };
		final int[] leapYear = { 101, 102, 103, 104, 105, 106, 107, 108, 109,
				110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121,
				122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 201, 202,
				203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214,
				215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226,
				227, 228, 229, 301, 302, 303, 304, 305, 306, 307, 308, 309,
				310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321,
				322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 401, 402,
				403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414,
				415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426,
				427, 428, 429, 430, 501, 502, 503, 504, 505, 506, 507, 508,
				509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520,
				521, 522, 523, 524, 525, 526, 527, 528, 529, 530, 531, 601,
				602, 603, 604, 605, 606, 607, 608, 609, 610, 611, 612, 613,
				614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625,
				626, 627, 628, 629, 630, 701, 702, 703, 704, 705, 706, 707,
				708, 709, 710, 711, 712, 713, 714, 715, 716, 717, 718, 719,
				720, 721, 722, 723, 724, 725, 726, 727, 728, 729, 730, 731,
				801, 802, 803, 804, 805, 806, 807, 808, 809, 810, 811, 812,
				813, 814, 815, 816, 817, 818, 819, 820, 821, 822, 823, 824,
				825, 826, 827, 828, 829, 830, 831, 901, 902, 903, 904, 905,
				906, 907, 908, 909, 910, 911, 912, 913, 914, 915, 916, 917,
				918, 919, 920, 921, 922, 923, 924, 925, 926, 927, 928, 929,
				930, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009,
				1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019,
				1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029,
				1030, 1031, 1101, 1102, 1103, 1104, 1105, 1106, 1107, 1108,
				1109, 1110, 1111, 1112, 1113, 1114, 1115, 1116, 1117, 1118,
				1119, 1120, 1121, 1122, 1123, 1124, 1125, 1126, 1127, 1128,
				1129, 1130, 1201, 1202, 1203, 1204, 1205, 1206, 1207, 1208,
				1209, 1210, 1211, 1212, 1213, 1214, 1215, 1216, 1217, 1218,
				1219, 1220, 1221, 1222, 1223, 1224, 1225, 1226, 1227, 1228,
				1229, 1230, 1231 };
		if (CommonOps.isLeapYear(year) == true) {
			if (day < 1 || day > 366)
				throw new InvalidParameterException("Invalid day: " + day);
			return year * 10000 + (leapYear[day - 1]);
		} else {
			if (day < 1 || day > 365)
				throw new InvalidParameterException("Invalid day: " + day);
			return year * 10000 + (nonLeanYear[day - 1]);
		}
	}

	/**
	 * Transform a set of {@code byte} to hex strings, for example, {1, 2, 18}
	 * will be transform to "010212".
	 * 
	 * @param byteArray
	 *            {@code byte} array.
	 * @return hex string.
	 */
	public static String byteToHexString(byte[] byteArray) {
		if (byteArray == null)
			throw new NullPointerException();
		int i, offset;
		StringBuffer buf = new StringBuffer("");
		for (offset = 0; offset < byteArray.length; offset++) {
			i = byteArray[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append('0');
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();

	}

	/**
	 * Calculate MD5 of given string.
	 * 
	 * @param input
	 *            {@link String} object.
	 * @return MD5 value in hex (string), return {@code null} if there was error
	 *         occurred.
	 */
	public static String getMD5(String input) {
		String MD5 = null;
		byte b[] = null;
		try {
			MD5 = new String(input);
			MessageDigest md = MessageDigest.getInstance("MD5");
			b = md.digest(MD5.getBytes());
			MD5 = CommonOps.byteToHexString(b);
		} catch (Exception e) {
			return null;
		}
		return MD5;
	}

	/**
	 * Combine the content of a {@link String} array.
	 * 
	 * @param str
	 *            string array.
	 * @return content of the array, strings are separated by comma.
	 */
	public static String getArrayContentString(String[] str) {
		StringBuilder builder = new StringBuilder();
		for (String string : str) {
			builder.append(string);
			builder.append(", ");
		}
		if (builder.length() != 0) {
			builder.deleteCharAt(builder.length() - 2);
			return builder.toString();
		} else
			return null;
	}

	/**
	 * Executes the specified string command.
	 * 
	 * @param cmd
	 *            command to run.
	 * @return {@code false} on the occasion that the command can not be
	 *         executed (wrong command, for example), otherwise {@code true}.<br>
	 *         Note: this is a non-blocking method. See {@link Runtime} for
	 *         details.
	 */
	public static boolean system(String cmd) {
		Runtime r = Runtime.getRuntime();
		try {
			r.exec(cmd);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Start time counting. 
	 * @return current time.
	 */
	public synchronized static long tick() {
		return System.currentTimeMillis();
	}
	
	/**
	 * Stop time counting. 
	 * @param time the starting time.
	 * @return time cost.
	 */
	public synchronized static long tock(long time) {
		return System.currentTimeMillis() - time;
	}
}
