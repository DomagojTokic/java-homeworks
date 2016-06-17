package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization provider class which uses *.properties files to translate
 * application to desired language.
 * 
 * @author Domagoj
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Currently active language.
	 */
	private String language;

	/**
	 * Resource bundle containing all translations of current language.
	 */
	private ResourceBundle bundle;

	/**
	 * Singleton instance of localization provider.
	 */
	private static LocalizationProvider instance;

	/**
	 * Creates instance of {@link LocalizationProvider}
	 */
	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.translation", locale);
	}

	/**
	 * Returns instance of {@link LocalizationProvider}
	 * 
	 * @return instance of LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}

	/**
	 * Sets new language, requests new bundle and notifies all listeners if new
	 * language is different from current.
	 * 
	 * @param language New language.
	 */
	public void setLanguage(String language) {
		if (this.language != language) {
			Locale locale = Locale.forLanguageTag(language);
			bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.translation",
					locale);
			this.language = language;
			fire();
		}
	}

	
	public String getString(String key) {
		return bundle.getString(key);
	}

}
