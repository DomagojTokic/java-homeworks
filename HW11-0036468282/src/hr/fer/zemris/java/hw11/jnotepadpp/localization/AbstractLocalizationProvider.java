package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.ArrayList;

/**
 * Abstract localization provider which implements operations with listeners
 * which are object that change names of elements on localization change.
 * 
 * @author Domagoj
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Localization listeners
	 */
	private ArrayList<ILocalizationListener> listeners;

	/**
	 * Listeners for deletion before next notification is sent to listeners.
	 */
	private ArrayList<ILocalizationListener> invalidListeners;

	/**
	 * Constructor for {@link AbstractLocalizationProvider}
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
		invalidListeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		invalidListeners.add(listener);
	}

	/**
	 * Notifies changes in localization to all listener components that need to
	 * know that.
	 */
	public void fire() {
		for (ILocalizationListener listener : invalidListeners) {
			listeners.remove(listener);
		}
		invalidListeners.clear();
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}

	}

}
