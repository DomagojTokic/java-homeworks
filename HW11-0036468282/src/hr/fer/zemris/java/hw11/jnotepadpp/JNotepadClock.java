package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * Simple clock component used in {@link JNotepadPP}'s info-bar.
 * 
 * @author Domagoj
 *
 */
public class JNotepadClock extends JComponent {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Storage of date and time string
	 */
	volatile String time;

	/**
	 * Flag for ending clock thread.
	 */
	volatile boolean stopRequested;

	/**
	 * Date and time formatter.
	 */
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

	/**
	 * Creates instance of {@link JNotepadClock}
	 */
	public JNotepadClock() {
		updateTime();

		Thread thread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
					if (stopRequested)
						break;
				} catch (Exception ex) {
				}
				SwingUtilities.invokeLater(() -> {
					updateTime();

				});
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Signals clock thread that it should end.
	 */
	public void stop() {
		stopRequested = true;
	}

	/**
	 * Updates image of clock to current time.
	 */
	private void updateTime() {
		time = formatter.format(LocalDateTime.now());
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension dim = getSize();
		Rectangle r = new Rectangle(ins.left, ins.top, dim.width - ins.left - ins.right,
				dim.height - ins.top - ins.bottom);
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(r.x, r.y, r.width, r.height);
		}
		g.setColor(getForeground());

		FontMetrics fm = g.getFontMetrics();
		int w = fm.stringWidth(time);
		int h = fm.getAscent();

		g.drawString(time, r.x + (r.width - w) / 2, r.y + r.height - (r.height - h) / 2);

	}

}
