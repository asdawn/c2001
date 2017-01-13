package net.c2001.base;

/**
 * The default message processor for this library. It just output all kinds of 
 * messages to console. 
 * 
 * @author Lin Dong
 *
 */
public class DefaultMessageProcessor implements MessageProcessor {

	/**
	 * Here is a singleton, but it still can be created if you want to
	 * create other instances.
	 */
	private static DefaultMessageProcessor instance = null;
	
	/**
	 * Here is a singleton, but it still can be created if you want to
	 * create other instances. It can be used as the default one of all
	 * {@link Object}.
	 * @return the unique instance of {@link DefaultMessageProcessor}.
	 */
	public static DefaultMessageProcessor getInstance() {
		if(instance == null) {
			instance = new DefaultMessageProcessor();
		}
		return instance;
	}
	
	
	@Override
	public void text(String text) {
		System.out.println(text);

	}

	@Override
	public void text(java.lang.Object object, String text) {
		System.out.println(object+":\n"+text);
	}

	@Override
	public void warning(String text) {
		System.out.println("Warning: "+text);
	}

	@Override
	public void warning(java.lang.Object object, String text) {
		System.out.println(object+":\nWarning: "+text);
	}

	@Override
	public void error(String text) {
		System.err.println("Error: "+text);
	}

	@Override
	public void error(java.lang.Object object, String text) {
		System.err.println(object+":\nError: "+text);
	}

	@Override
	public void fatal(String text) {
		System.err.println("Fatal error: "+text);
	}

	@Override
	public void fatal(java.lang.Object object, String text) {
		System.err.println(object+":\nFatal error: "+text);
	}

	@Override
	public void exception(Exception e, String text) {
		System.err.println("Exception: "+text);
		e.printStackTrace();
	}

	@Override
	public void exception(java.lang.Object object, Exception e, String text) {
		System.err.println(object+":\nException: "+text);
		e.printStackTrace();
	}

	@Override
	public void progress(double progress, String title, String text) {
		System.out.println("Progress: "+text);
		System.out.println(title);
		System.out.println((progress<0?"unknown":(int)progress)+"%");
	}

	@Override
	public void progress(java.lang.Object object, double progress,
			String title, String text) {
		System.out.println(object+":\n Progress: "+text);
		System.out.println(title);
		System.out.println((progress<0?"unknown":(int)progress));
	}

}
