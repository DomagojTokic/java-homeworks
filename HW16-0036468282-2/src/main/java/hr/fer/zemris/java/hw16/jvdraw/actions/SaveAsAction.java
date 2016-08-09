package hr.fer.zemris.java.hw16.jvdraw.actions;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.draw.DrawingModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * "Save As" action.
 */
public class SaveAsAction extends AbstractSaveAction{

    /** UID */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new "Save As" action
     * @param appFrame Main application frame
     * @param model
     */
    public SaveAsAction(JVDraw appFrame, DrawingModel model) {
        super(appFrame, model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveFileChooser.setDialogTitle("Save As");
        if (executeSaveDialog() == JFileChooser.APPROVE_OPTION) {
            Path filePath = Paths.get(path);
            writeFile(filePath);
        }
    }

}
