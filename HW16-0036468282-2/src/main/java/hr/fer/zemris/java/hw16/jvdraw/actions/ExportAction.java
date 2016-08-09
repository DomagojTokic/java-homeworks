package hr.fer.zemris.java.hw16.jvdraw.actions;


import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.draw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.draw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.objects.BoundingBox;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Action for exporting drawn image to one of 3 defined formats: JPG, PNG and GIF. Image is cropped version of displayed
 * {@link JDrawingCanvas} so that all white space surrounding image is removed.
 */
public class ExportAction extends AbstractAction {

    /** UID */
    private static final long serialVersionUID = 1L;

    /** Main application frame */
    protected JVDraw appFrame;

    /** Drawing canvas */
    private JDrawingCanvas canvas;

    /** JPG file extension filter */
    private FileNameExtensionFilter jpgExtFilter = new FileNameExtensionFilter("JPG (.jpg)", "jpg", "jpg");

    /** PNG file extension filter */
    private FileNameExtensionFilter pngExtFilter = new FileNameExtensionFilter("PNG (.png)", "png", "png");

    /** GIF file extension filter */
    private FileNameExtensionFilter gifExtFilter = new FileNameExtensionFilter("GIF (.gif)", "gif", "gif");

    /**
     * Modified {@link JFileChooser} for saving operation so that if file with
     * given name already exists, user will be asked for confirmation - saving
     * will overwrite old file.
     */
    protected JFileChooser exportFileChooser = new JFileChooser() {

        /** Serialization version control number. */
        private static final long serialVersionUID = 1L;

        @Override
        public void approveSelection() {
            if (this.getSelectedFile().exists()) {
                int decision = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to overwrite?");
                if (decision != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            super.approveSelection();
        }
    };

    /**
     * Creates new export action.
     *
     * @param appFrame Main application frame
     * @param model    Graphical objects model
     * @param canvas
     */
    public ExportAction(JVDraw appFrame, DrawingModel model, JDrawingCanvas canvas) {
        this.appFrame = appFrame;
        this.canvas = canvas;

        exportFileChooser.setDialogTitle("Export Image");
        exportFileChooser.removeChoosableFileFilter(exportFileChooser.getFileFilter());
        exportFileChooser.setFileFilter(jpgExtFilter);
        exportFileChooser.addChoosableFileFilter(pngExtFilter);
        exportFileChooser.addChoosableFileFilter(gifExtFilter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int option = exportFileChooser.showSaveDialog(appFrame);
        if (option != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(appFrame, "Image not saved",
                    "Image has not been saved", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String extension = ((FileNameExtensionFilter) exportFileChooser.getFileFilter()).getExtensions()[0];
        Path filePath = exportFileChooser.getSelectedFile().toPath();

        if (!filePath.endsWith(extension)) {
            filePath = Paths.get(filePath.toAbsolutePath() + "." + extension);
        }
        exportImage(filePath, extension);
    }

    /**
     * Exports image in format specified by extension to file specified in file path argument.
     *
     * @param filePath  Export file path
     * @param extension Format of exported image
     */
    private void exportImage(Path filePath, String extension) {

        BoundingBox boundingBox = getBoundingBox();

        BufferedImage image = new BufferedImage(
                canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_3BYTE_BGR
        );

        Graphics2D g = image.createGraphics();
        canvas.paintAll(g);
        g.dispose();

        image = image.getSubimage(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMaxX() - boundingBox.getMinX(), boundingBox.getMaxY() - boundingBox.getMinY());
        try {
            ImageIO.write(image, extension, filePath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(appFrame, "Image saved",
                "Image has been saved", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Returns {@link BoundingBox} surrounding all shapes drawn on canvas.
     *
     * @return {@link BoundingBox} of all canvas shapes.
     */
    private BoundingBox getBoundingBox() {
        Integer minX = Integer.MAX_VALUE;
        Integer maxX = Integer.MIN_VALUE;
        Integer minY = Integer.MAX_VALUE;
        Integer maxY = Integer.MIN_VALUE;

        if (canvas.getModel().getSize() == 0) {
            minX = 0;
            maxX = 0;
            minY = 0;
            maxY = 0;
        }

        BoundingBox tempBBox;
        for (int i = 0; i < canvas.getModel().getSize(); i++) {
            tempBBox = canvas.getModel().getObject(i).getBoundingBox();
            if (tempBBox.getMinX() < minX) {
                minX = tempBBox.getMinX();
            }
            if (tempBBox.getMaxX() > maxX) {
                maxX = tempBBox.getMaxX();
            }
            if (tempBBox.getMinY() < minY) {
                minY = tempBBox.getMinY();
            }
            if (tempBBox.getMaxY() > maxY) {
                maxY = tempBBox.getMaxY();
            }
        }

        return new BoundingBox(minX, maxX, minY, maxY);
    }
}
