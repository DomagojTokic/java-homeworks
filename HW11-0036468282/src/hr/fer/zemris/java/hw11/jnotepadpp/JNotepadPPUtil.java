package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.UnaryOperator;

/**
 * Utility class for {@link JNotepadPP}. Stores all operations which aren't
 * directly linked with application GUI.
 * 
 * @author Domagoj
 *
 */
public class JNotepadPPUtil {

	/**
	 * Inverts casing of recieved text.
	 */
	public static final UnaryOperator<String> INVERT_CASE = (text) -> {
		char[] znakovi = text.toCharArray();
		for (int i = 0; i < znakovi.length; i++) {
			if (Character.isLowerCase(znakovi[i])) {
				znakovi[i] = Character.toUpperCase(znakovi[i]);
			} else {
				znakovi[i] = Character.toLowerCase(znakovi[i]);
			}
		}
		return new String(znakovi);
	};

	/**
	 * Sets all string characters to upper case.
	 */
	public static final UnaryOperator<String> To_UPPER_CASE = (text) -> {
		return text.toUpperCase();
	};

	/**
	 * Sets all string characters to lower case.
	 */
	public static final UnaryOperator<String> To_LOWER_CASE = (text) -> {
		return text.toLowerCase();
	};

	/**
	 * Reads all bytes from input stream.
	 * 
	 * @param is Input stream
	 * @return Array of bytes
	 * @throws IOException if method is unable to read from input stream.
	 */
	public static byte[] readAllBytes(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		return buffer.toByteArray();
	}

	/**
	 * Sorts given string by it's lines with given language {@link Locale}.
	 * 
	 * @param text Test for sorting
	 * @param locale Language locale
	 * @param reversed State of reversed sorting
	 * @return Sorted text by lines.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String sort(String text, Locale locale, boolean reversed) {
		Collator collator = Collator.getInstance(locale);
		ArrayList<String> list = new ArrayList(Arrays.asList(text.split("\n")));
		if (reversed) {
			list.sort(collator.reversed());
		} else {
			list.sort(collator);
		}
		String result = "";
		for (String line : list) {
			result += line + "\n";
		}
		return result;
	}

}
