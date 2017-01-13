package net.c2001.base;

/*
 * Though all objects should be net.c2001.base.Ojbect, here we use 
 * {@link java.lang.Object} in case.
 */
import java.lang.Object;

/**
 * Message types used in this library. There are pairs of methods, all
 * methods with an {@link Object} parameter are designed for debugging, though 
 * not strictly limited.
 * @author Lin Dong
 *
 */
public abstract interface Messages {
	
	
	/**
	 * Output some text.
	 * @param text text to output.
	 */
	abstract public void text(String text);
	
	/**
	 * Output some text.
	 * @param object the calling object.
	 * @param text text to output.
	 */
	abstract public void text(Object object, String text);
	
	/**
	 * Output a warning.
	 * @param text text to output.
	 */
	abstract public void warning(String text);
	
	/**
	 * Output a warning.
	 * @param object the calling object.
	 * @param text text to output.
	 */
	abstract public void warning(Object object, String text);
	
	/**
	 * Report an error.
	 * @param text text to output.
	 */
	abstract public void error(String text);
	
	/**
	 * Report an error.
	 * @param object the calling object.
	 * @param text text to output.
	 */
	abstract public void error(Object object, String text);
	
	/**
	 * Output a fatal error, it should be thread blocking.
	 * @param text text to output.
	 */
	abstract public void fatal(String text);
	
	/**
	 * Output a fatal error, it should be thread blocking.
	 * @param object the calling object.
	 * @param text text to output.
	 */
	abstract public void fatal(Object object, String text);
	
	/**
	 * Report an exception.
	 * @param e the exception to report.
	 * @param text extra messages.
	 */
	abstract public void exception(Exception e, String text);
	
	/**
	 * Report an exception.
	 * @param object the calling object.
	 * @param e the exception to report.
	 * @param text extra messages.
	 */
	abstract public void exception(Object object, Exception e, String text);
	
	/**
	 * Report progress information.
	 * @param progress percentage of progress.
	 * @param title the title of the progress bar (if available).
	 * @param text extra messages to show under the progress bar (if available).
	 */
	abstract public void progress(double progress, String title, String text);
	
	/**
	 * Report progress information.
	 * @param object the calling object.
	 * @param progress percentage of progress.
	 * @param title the title of the progress bar (if available).
	 * @param text extra messages to show under the progress bar (if available).
	 */
	abstract public void progress(Object object, double progress, 
			String title, String text);

}