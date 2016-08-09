package hr.fer.zemris.java.hw16.jvdraw.actions;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.draw.DrawingModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * "Save" action
 */
public class SaveAction extends AbstractSaveAction {

    /** UID */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new "Save" action
     * @param appFrame Main application frame
     * @param model
     */
    public SaveAction(JVDraw appFrame, DrawingModel model) {
        super(appFrame, model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int option = JFileChooser.APPROVE_OPTION;
        if (path == null) {
            saveFileChooser.setDialogTitle("Save document");
            option = executeSaveDialog();
        }
        if (option == JFileChooser.APPROVE_OPTION) {
            Path filePath = Paths.get(path);
            writeFile(filePath);
        }
    }

}
