package hr.fer.zemris.java.hw16.jvdraw.actions;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.draw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.draw.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.draw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Action for opening JVD files in JVDraw.
 */
public class OpenAction extends AbstractAction {

    /** UID */
    private static final long serialVersionUID = 1L;

    /** Main application frame */
    private JVDraw appFrame;

    /** Drawing canvas */
    private JDrawingCanvas canvas;

    /** Drawing data model */
    private DrawingModel model;

    /** Drawing object list model */
    private DrawingObjectListModel listModel;

    /**
     * Creates new instance of open action.
     * @param appFrame Frame which uses this action
     * @param canvas
     * @param model
     * @param listModel
     */
    public OpenAction(JVDraw appFrame, JDrawingCanvas canvas, DrawingModel model, DrawingObjectListModel listModel) {
        this.appFrame = appFrame;
        this.canvas = canvas;
        this.model = model;
        this.listModel = listModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("JVD File", "jvd", "jvd"));
        fc.setDialogTitle("Open");
        if (fc.showOpenDialog(appFrame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = fc.getSelectedFile();
        Path filePath = file.toPath();
        if (!Files.isReadable(filePath)) {
            JOptionPane.showMessageDialog(appFrame,
                    String.format("File is not readable",
                            file.getAbsolutePath()),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            model.clear();

            for (String line : lines) {
                model.add(GeometricalObject.fromString(line));
            }
        } catch (IOException ignorable) {
        }
    }

}
