package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Class extending {@link LocalizationProviderBridge} which attaches window
 * listener on instance of {@link JFrame} so that when it opens, it connects
 * with localization provider and when is closes, it disconnects from
 * localization provider.
 * 
 * @author Domagoj
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge implements WindowListener {

	/**
	 * Creates new instance of {@link FormLocalizationProvider}.
	 * 
	 * @param provider Localization provider
	 * @param frame Frame which holds components for localization
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		connect();
	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {
		disconnect();
	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

}
