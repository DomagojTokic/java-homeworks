package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * Serves as a bridge between {@link LocalizationProvider} and frame components.
 * Provides application with property of connecting and disconnecting from
 * LocalizationProvider so that garbage collector can do it's job when object
 * which listened to LocalizationProvider is no longer active.
 * 
 * @author Domagoj
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Flag of bridge connection status.
	 */
	boolean connected;

	/**
	 * Bridges {@link LocalizationProvider} listener.
	 */
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			fire();
		}
	};;

	/**
	 * Reference to localization provider.
	 */
	ILocalizationProvider parent;

	/**
	 * Creates new instance of {@link LocalizationProviderBridge}
	 * 
	 * @param parent Localization provider.
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
	}

	@Override
	public String getString(String key) {
		if (!connected) {
			throw new RuntimeException("Unable to request from disconnected provider.");
		}
		return parent.getString(key);
	}

	/**
	 * Disconnects for localization provider
	 */
	public void disconnect() {
		parent.removeLocalizationListener(listener);
		connected = false;
	}

	/**
	 * Connects to localization provider
	 */
	public void connect() {
		if (!connected) {
			parent.addLocalizationListener(listener);
			connected = true;
		}
	}

}
