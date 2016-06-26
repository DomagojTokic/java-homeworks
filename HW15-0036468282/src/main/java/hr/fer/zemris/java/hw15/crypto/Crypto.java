package hr.fer.zemris.java.hw15.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Class for crypting strings with SHA-1 algorithm.
 * 
 * @author Domagoj Tokić
 *
 */
public class Crypto {

	/**
	 * Encrypts given text to
	 * 
	 * @param text Text for encryption
	 * @return Encrypted text
	 */
	public static String encrypt(String text) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(text.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	/**
	 * Converts byte array to String.
	 * 
	 * @param hash Byte array
	 * @return String representation of byte array.
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}

		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
