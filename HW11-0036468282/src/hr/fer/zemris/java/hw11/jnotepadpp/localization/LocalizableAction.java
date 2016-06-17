package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Action class that needs to change it's name and short description when
 * localization change occurs.
 * 
 * @author Domagoj
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new instance of {@link LocalizableAction} by adding it's
	 * localization listener to localization provider.
	 * 
	 * @param name
	 * @param localizationProvider
	 */
	public LocalizableAction(String name, ILocalizationProvider localizationProvider) {
		localizationProvider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, localizationProvider.getString(name + ".key"));
				putValue(Action.SHORT_DESCRIPTION,
						localizationProvider.getString(name + ".shDesc"));
			}
		});
	}
}
