package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * Interface to localization providers. Providers main function is to hold
 * listeners and return strings for translation keys.
 * 
 * @author Domagoj
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds new localization listener
	 * 
	 * @param listener
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Removes existing localization listener
	 * 
	 * @param listener
	 */
	void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Returns translation for given key.
	 * 
	 * @param key Key of translation.
	 * @return translation
	 */
	String getString(String key);

}
