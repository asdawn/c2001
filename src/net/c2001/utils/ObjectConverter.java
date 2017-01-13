package net.c2001.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Parse {@link Object} from byte[] or convert {@link Object} to byte[].
 * 
 * @author Lin Dong
 * 
 */
public class ObjectConverter {

	/**
	 * Parse an {@link Object} from byte array.
	 * 
	 * @param bytes
	 *            array of byte.
	 * @return the {@link Object} on success, {@code null} on failure.
	 */
	public static Object parstObject(byte[] bytes) {
		
		try (ByteArrayInputStream bios = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bios)) {
			return ois.readObject();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Convert an object to byte array.
	 * @param object the {@link Object} to convert.
	 * @return a byte array on succes, {@code null} on failure.
	 */
	public static byte[] toBytes(Object object){
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos)){
			oos.writeObject(object);
			oos.flush();
			return baos.toByteArray();
		}catch (Exception e) {
			return null;
		}
	}
}
