package hr.fer.zemris.java.hw18;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for accessing photos, photo thumbnails and information about photos.
 * 
 * @author Domagoj Tokić
 *
 */
public class PhotosDB {

	/** Path to description file */
	private static final String DESC_FILE = "/WEB-INF/opisnik.txt";

	/** Path to full sized photos */
	private static final String PHOTO_PATH = "/WEB-INF/slike";

	/** Path to thumbnail photos */
	private static final String THUMBNAIL_PATH = "/WEB-INF/thumbnails";

	/** Thumbnail photo width */
	private static final int THUMBNAIL_WIDTH = 150;

	/** Thumbnail photo height */
	private static final int THUMBNAIL_HEIGHT = 150;

	/**
	 * Returns set of tags.
	 * 
	 * @param req {@link HttpServletRequest} object
	 * @param resp {@link HttpServletResponse} object
	 * @return set of tags.
	 */
	public static Set<String> getTags(HttpServletRequest req,
			HttpServletResponse resp) {
		String fileName = req.getServletContext().getRealPath(DESC_FILE);
		Set<String> set = new HashSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));) {
			while (reader.readLine() != null) {
				reader.readLine(); // skipping line
				String tagsLine = reader.readLine();
				if (tagsLine != null) {
					String[] split = tagsLine.split(",");
					for (String tag : split) {
						set.add(tag.trim());
					}
				}

			}
			reader.close();
		} catch (IOException e) {
			try {
				req.setAttribute("message", "Unable to read tags");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ignorable) {
			}
		}

		return set;
	}

	/**
	 * Returns list photo file names for a tag.
	 * 
	 * @param tag Photo tag
	 * @param req {@link HttpServletRequest} object
	 * @param resp {@link HttpServletResponse} object
	 * @return list photo file names for a tag.
	 */
	public static List<String> getPhotoNamesForTag(String tag, HttpServletRequest req,
			HttpServletResponse resp) {
		String fileName = req.getServletContext().getRealPath(DESC_FILE);
		List<String> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));) {

			while ((fileName = reader.readLine()) != null) {
				reader.readLine(); // skipping line
				String tagsLine = reader.readLine();

				if (tagsLine != null) {
					String[] split = tagsLine.split(",");

					for (String innerTag : split) {
						if (innerTag.trim().equals(tag)) {
							list.add(fileName);
							break;
						}
					}
				}
			}

			reader.close();
			return list;

		} catch (IOException e) {
			try {
				req.setAttribute("message", "Unable to fetch photos for tag " + tag);
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ignorable) {
			}
		}

		return list;
	}

	/**
	 * Returns photo in array of bytes.
	 * 
	 * @param name Photo file name
	 * @param req {@link HttpServletRequest} object
	 * @param resp {@link HttpServletResponse} object
	 * @return photo in array of bytes.
	 */
	public static byte[] getPhoto(String name, HttpServletRequest req,
			HttpServletResponse resp) {
		String filePath = req.getServletContext().getRealPath(PHOTO_PATH + "/" + name);

		try {
			BufferedImage photo = ImageIO.read(new File(filePath));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(photo, "jpg", baos);

			return baos.toByteArray();

		} catch (IOException e) {
			try {
				req.setAttribute("message", "Unable to fetch photo " + name);
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ignorable) {
			}
		}
		return null;
	}

	/**
	 * Returns photo description.
	 * 
	 * @param name Photo file name
	 * @param req {@link HttpServletRequest} object
	 * @param resp {@link HttpServletResponse} object
	 * @return photo description
	 */
	public static String getPhotoDesc(String name, HttpServletRequest req,
			HttpServletResponse resp) {
		String fileName = req.getServletContext().getRealPath(DESC_FILE);
		String description = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));) {
			while ((fileName = reader.readLine()) != null) {
				if (fileName.equals(name)) {
					description = reader.readLine();
					break;
				}
			}

			reader.close();
			if (description != null) {
				return description;
			} else {
				return "";
			}

		} catch (IOException e) {
			try {
				req.setAttribute("message", "Unable to fetch photo descripton for " + name);
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ignorable) {
			}
		}

		return "";
	}

	/**
	 * Returns thumbnail photo in array of bytes. If it doesn't exist, it
	 * creates it before returning.
	 * 
	 * @param name Photo (thumbnail) name
	 * @param req {@link HttpServletRequest} object
	 * @param resp {@link HttpServletResponse} object
	 * @return thumbnail photo in array of bytes
	 */
	public static byte[] getThumbnail(String name, HttpServletRequest req,
			HttpServletResponse resp) {

		String filePath = req.getServletContext().getRealPath(THUMBNAIL_PATH + "/" + name);

		if (!(new File(filePath)).exists()) {
			String src = req.getServletContext().getRealPath(PHOTO_PATH + "/" + name);
			createThumbnail(src, filePath);
		}

		try {
			BufferedImage thumbnail = ImageIO.read(new File(filePath));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(thumbnail, "jpg", baos);

			return baos.toByteArray();

		} catch (IOException ignorable) {
		}
		return null;
	}

	/**
	 * Creates new thumbnail photo.
	 * 
	 * @param src Source file
	 * @param dest Destination file
	 */
	private static void createThumbnail(String src, String dest) {
		BufferedImage bsrc, bdest;

		try {
			bsrc = ImageIO.read(new File(src));

			bdest = new BufferedImage(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bdest.createGraphics();
			g.setComposite(AlphaComposite.Src);
			g.drawImage(bsrc, 0, 0, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, null);
			g.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bdest, "jpg", baos);
			byte[] bytes = baos.toByteArray();

			FileOutputStream fos = new FileOutputStream(dest);
			try {
				fos.write(bytes);
			} finally {
				fos.close();
			}
		} catch (Exception e) {
			System.out.println(
					"This image can not be resized. Please check the path and type of file " + src);
		}
	}

}
