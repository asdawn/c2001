package net.c2001.utils;

/**
 * A lazy wrapping for sleep().
 * @author Lin Dong
 */
public class TimeControl
{
	/**
	 * Instance of this class is not needed.
	 */
	private TimeControl()
	{
	}

	/**
	 * Call {@linkplain Thread}.sleep().
	 * 
	 * @param time
	 *      time in milliseconds.
	 */
	public static void sleep(int time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
