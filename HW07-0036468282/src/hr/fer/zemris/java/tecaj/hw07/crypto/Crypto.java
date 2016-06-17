package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Allows the user to encrypt/decrypt given file using the AES crypto algorithm
 * and the 128-bit encryption key or calculate and check the SHA-256 file
 * digest.
 * 
 * @author Domagoj Tokić
 *
 */
public class Crypto {

	/**
	 * Entrance into crypto program.
	 * 
	 * @param args - args[0] Operation (encrypt/decrypt/checksha)
	 *            args[1] Source file (encrypt/decrypt/checksha)
	 *            args[2] Destination (encrypt/decrypt)
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 3) {
				switch (args[0]) {
				case "encrypt":
					crypt(args[1], args[2], Cipher.ENCRYPT_MODE);
					break;
				case "decrypt":
					crypt(args[1], args[2], Cipher.DECRYPT_MODE);
					break;
				default:
					invalidInput();
				}

			} else if (args.length == 2) {
				if (args[0].equals("checksha")) {
					checksha(args[1]);
				} else {
					invalidInput();
				}

			} else {
				invalidInput();
			}
		} catch (Exception e) {
			System.err.println("Something got wrong");
		}

	}

	/**
	 * Notifies user about invalid input and exits program.
	 */
	private static void invalidInput() {
		System.err.println("Invalid input");
		System.exit(1);
	}

	/**
	 * Does encryption and decryption depending on mode.
	 * 
	 * @param input Sourse path
	 * @param output Destination path
	 * @param mode Crypting mode
	 * @throws Exception If something got wrong with crypting process
	 */
	private static void crypt(String input, String output, int mode) throws Exception {
		BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));

		System.out.print(
				"Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
		String keyText = stdInReader.readLine();

		System.out.print(
				"Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
		String ivText = stdInReader.readLine();

		SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(mode, keySpec, paramSpec);

		stdInReader.close();

		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(input)));
				BufferedOutputStream os = new BufferedOutputStream(
						new FileOutputStream(new File(output)))) {
			int bytesRead;
			byte[] buffer = new byte[4096];

			while ((bytesRead = is.read(buffer)) > 0) {
				buffer = cipher.update(buffer, 0, bytesRead);
				os.write(buffer, 0, buffer.length);
			}
			byte[] finalCode = cipher.doFinal();
			os.write(finalCode);

			os.close();
			is.close();
		}

		if (mode == Cipher.ENCRYPT_MODE) {
			System.out.println("Encryption completed. Generated file " + new File(output).getName()
					+ " based on file " + new File(input).getName() + ".");
		} else {
			System.out.println("Decryption completed. Generated file " + new File(output).getName()
					+ " based on file " + new File(input).getName() + ".");
		}

	}

	/**
	 * Transforms hexadecimal string to byte array. If string isn't hexadecimal,
	 * method returns null.
	 * 
	 * @param text Hexadecimal text
	 * @return byte array
	 */
	public static byte[] hextobyte(String text) {
		byte[] byteText = new byte[text.length() / 2];

		for (int i = 1; i < text.length(); i += 2) {
			if (text.substring(i - 1, i).matches("[0-9a-fA-F]")
					&& text.substring(i, i + 1).matches("[0-9a-fA-F]")) {
				int b = Character.digit(text.charAt(i - 1), 16);
				b = b << 4;
				b += Character.digit(text.charAt(i), 16);
				byteText[i / 2] = (byte) b;
			} else {
				return null;
			}

		}
		return byteText;
	}

	/**
	 * Check the SHA-256 file digest.
	 * 
	 * @param input Source path
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private static void checksha(String input) throws NoSuchAlgorithmException, IOException {

		File file = new File(input);

		if (!file.isFile()) {
			invalidInput();
		}

		BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Please provide expected sha-256 digest for " + file.getName() + ":\n> ");
		byte[] expected = hextobyte(stdInReader.readLine());

		stdInReader.close();

		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(file))) {

			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) > 0) {
				digest.update(buffer, 0, bytesRead);
			}

			byte[] signature = digest.digest();

			if (Arrays.equals(signature, expected)) {
				System.out.println("Digesting completed. Digest of " + file.getName()
						+ " matches expected digest.");
			} else {

				String signString = "";
				for (byte b : signature) {
					signString += String.format("%02x", b & 0xff);
				}

				System.out.println("Digesting completed. Digest of hw07test.bin does"
						+ " not match the expected digest. Digest was:\n" + signString);
			}

		} catch (IOException e) {
			System.err.println("Unable to read from file " + file.getName());
			System.exit(1);
		}
	}

}
