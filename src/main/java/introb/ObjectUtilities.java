package introb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * @author hitesh
 * 
 * This Utility class provides facilities for deep cloning and comparing two
 * serializable object.
 */
public class ObjectUtilities implements Cloneable, Serializable {

	
	/**
	 * Deep clone an object. To use this - use code such as:
	 * 
	 * public Object clone() { return ObjectUtilities.clone(this); }
	 * 
	 * @param object - object to be copied
	 * @return - a copy of the object
	 */
	public static Object clone(Object object) {
		byte[] ba = serializeToByteArray(object);
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(ba);
			ObjectInputStream ois = new ObjectInputStream(bais);
			object = ois.readObject();
			ois.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return object;
	}


	/**
	 * Deep test for equality. Note: all objects must be serializable To use
	 * this - use code such as:
	 * 
	 * public boolean equals(Object object) { 
	 * 		return ObjectUtilities.equals(this, object); 
	 * }
	 * 
	 * @param object1 - the first object
	 * @param object2 - the second object
	 * @return - true iff the two objects have equal contents
	 */
	public static boolean equals(Object object1, Object object2) {
		return equals(serializeToByteArray(object1), serializeToByteArray(object2));
	}


	/**
	 * Deep hash code. Note: all objects must be serializable To use this - use
	 * code such as:
	 * 
	 * public int hashCode(Object object) { 
	 * 		return ObjectUtilities.hashCode(this); 
	 * }
	 * 
	 * @param object - the object to be hashed
	 * @return - the hash code
	 */
	public static int hashCode(Object object) {
		return hashCode(object, new CRC32());
	}


	/**
	 * Deep hash code. Note: all objects must be serializable To use this - use
	 * code such as:
	 * 
	 * public int hashCode(Object object) { 
	 * 		return ObjectUtilities.hashCode(this, new CRC32()); 
	 * }
	 * 
	 * @param object - the object to be hashed
	 * @param checksum - java.util.zip.Checksum instance to be used to compute the hash
	 * @return - the hash code
	 */
	public static int hashCode(Object object, Checksum checksum) {
		byte[] bytes = serializeToByteArray(object);
		checksum.update(bytes, 0, bytes.length);
		return (int) checksum.getValue();
	}


	/**
	 * This method will convert a serializable object to byte array.
	 * 
	 * @param object
	 * @return
	 */
	private static byte[] serializeToByteArray(Object object) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return baos.toByteArray();
	}


	/**
	 * This method will compare two byte arrays.
	 * 
	 * @param bytes1
	 * @param bytes2
	 * @return
	 */
	private static boolean equals(byte[] bytes1, byte[] bytes2) {
		int length = bytes1.length;
		if (length != bytes2.length) {
			return false;
		}
		for (int i = length; --i >= 0;) {
			if (bytes1[i] != bytes2[i]) {
				return false;
			}
		}
		return true;
	}
}