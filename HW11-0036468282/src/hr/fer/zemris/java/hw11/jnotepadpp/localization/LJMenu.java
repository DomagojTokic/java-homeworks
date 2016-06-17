package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.io.Serializable;

import javax.swing.JMenu;

/**
 * Localizable version of {@link JMenu} class.
 * 
 * @author Domagoj
 *
 */
public class LJMenu extends JMenu implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates instance of {@link LocalizableAction}
	 * 
	 * @param name Key of menu localizations
	 * @param localizationProvider Localization provider
	 */
	public LJMenu(String name, ILocalizationProvider localizationProvider) {
		super(localizationProvider.getString(name + ".key"));
		localizationProvider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				setText(localizationProvider.getString(name + ".key"));
			}
		});
	}

}
