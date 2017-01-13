package net.c2001.utils;

/**
 * 2^0 to 2^30.
 * @author Lin Dong
 */
public interface Pow2
{

	/**
	 * 2^0 to 2^30 in an int[31].<br>
	 * Note: in java there's no {@code unsigned int}.
	 */
	public final static int[] POW2 = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512,
	        1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144,
	        524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432,
	        67108864, 134217728, 268435456, 536870912, 1073741824 };
}
