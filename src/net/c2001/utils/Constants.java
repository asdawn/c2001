package net.c2001.utils;

/**
 * Some constants in the {@code net.c2001} library.
 * @author Lin Dong
 *
 */
public interface Constants
{
	/**
	 * Carriage return under Windows.
	 */
	public static final String CR = "\r\n" ;
	
	/**
	 * ln(2).
	 */
	public static final double LN2 = Math.log(2);
	
	
	/**
	 * Names of months: JAN, FEB, ..., DEC.
	 */
	public static String[] MONTHS = {
		"JAN","FEB","MAR","APR",
		"MAY","JUN","JUL","AUG",
		"SEP","OCT","NOV","DEC"};
	
	/**
	 * Short names of quarters: Q1, Q2, Q3, Q4.
	 */
	public static String[] QUARTERS = {"Q1","Q2","Q3","Q4"};
	
	/**
	 * Names of months: Spring, Summer, Autumn, Winter.
	 */
	public static String[] SEASONS = {"Spring","Summer","Autumn","Winter"};
}
