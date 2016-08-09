package hr.fer.zemris.java.hw16.jvdraw.actions;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.draw.DrawingModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Abstract save action containing variables and methods used both by
 * {@link SaveAction} and {@link SaveAsAction}
 */
public abstract class AbstractSaveAction extends AbstractAction {

    /** Main application frame */
    protected JVDraw appFrame;

    /** Drawing data model */
    protected DrawingModel model;

    /** Path to previously saved JVD image */
    protected String path;

    public AbstractSaveAction(JVDraw appFrame, DrawingModel model) {
        this.appFrame = appFrame;
        this.model = model;
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("JVD File", "jvd", "jvd"));
    }

    /**
     * Modified {@link JFileChooser} for saving operation so that if file with
     * given name already exists, user will be asked for confirmation - saving
     * will overwrite old file.
     */
    protected JFileChooser saveFileChooser = new JFileChooser() {

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
     * Writes byte data from active tab (text) and writes it to specified path.
     *
     * @param filePath Writing file path.
     */
    protected void writeFile(Path filePath) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < model.getSize(); i++) {
            builder.append(model.getObject(i).getJVDString());
            builder.append("\n");
        }

        byte[] data = builder.toString().getBytes();

        try {
            if(!filePath.toAbsolutePath().toString().toLowerCase().endsWith(".jvd")) {
                filePath = Paths.get(filePath.toAbsolutePath().toString() + ".jvd");
            }
            Files.write(filePath, data);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(appFrame, "Unable to write",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(appFrame, "File Saved",
                "File is saved", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Opens save dialog where user defines name and place of saving file.
     *
     * @return Chosen option
     */
    protected int executeSaveDialog() {
        int option = saveFileChooser.showSaveDialog(appFrame);
        if (option != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(appFrame, "Document not saved",
                    "Document is not not saved", JOptionPane.WARNING_MESSAGE);
            return option;
        }
        Path filePath = saveFileChooser.getSelectedFile().toPath();
        path = filePath.toString();
        return option;
    }

}
