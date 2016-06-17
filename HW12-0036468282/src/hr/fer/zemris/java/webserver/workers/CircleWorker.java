package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class which creates circle and sends it to {@link RequestContext} instance.
 * 
 * @author Domagoj
 *
 */
public class CircleWorker implements IWebWorker {

	/**
	 * Size of image and circle
	 */
	private static final int SIZE = 200;
	
	@Override
	public synchronized void processRequest(RequestContext context) {
		BufferedImage bim = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, SIZE - 1, SIZE - 1);

		g2d.fillRect(0, 0, SIZE, SIZE);
		g2d.setColor(Color.green);
		g2d.fill(circle);
		g2d.dispose();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.setMimeType("image/png");
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
