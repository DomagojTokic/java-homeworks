package hr.fer.zemris.java.hw15.forms;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract form class for all forms necessary in model.
 * 
 * @author Domagoj Tokić
 *
 */
public abstract class AbstractForm {

	/** Map of error messages needed when client makes invalid form input. */
	Map<String, String> validity = new HashMap<>();

	/**
	 * Checks validity of input data and sets error messages.
	 */
	public abstract void checkValidity();

	/**
	 * Returns error messages map.
	 * @return error messages map.
	 */
	public Map<String, String> getValidity() {
		return validity;
	}

	/**
	 * Checks if form is valid.
	 * @param form Form
	 * @return True if it's valid, else false.
	 */
	public static boolean isFormValid(AbstractForm form) {
		boolean valid = true;
		for (String message : form.getValidity().values()) {
			if (!message.equals("")) {
				valid = false;
				break;
			}
		}
		return valid;
	}

}
