package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw16.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.draw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.draw.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.draw.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.draw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectType;

/**
 * Simple Paint application.
 *
 * @author Domagoj Tokić
 */
public class JVDraw extends JFrame {

    /** Serialization version control number. */
    private static final long serialVersionUID = 1L;

    /** Drawing data model */
    private static DrawingModel model = new DrawingModelImpl();

    /** Background color chooser component */
    private JColorArea backgroundColChooser = new JColorArea(Color.RED, this, "background color");

    /** Foreground color chooser component */
    private JColorArea foregroundColChooser = new JColorArea(Color.BLUE, this, "foreground color");

    /** Drawing canvas */
    private JDrawingCanvas canvas = new JDrawingCanvas(model, backgroundColChooser, foregroundColChooser);

    /** Drawing object list model */
    private DrawingObjectListModel listModel = new DrawingObjectListModel(model);

    /**
     * Creates new instance of {@link JVDraw}.
     */
    public JVDraw() {
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(0, 0);
        setSize(1024, 768);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
        }

        initGUI();
    }

    /**
     * Initializes all components on main frame.
     */
    private void initGUI() {
        getContentPane().setLayout(new BorderLayout());

        createMenuBar();
        createToolbar();
        createInfoBar();
        createList();

        this.getContentPane().add(canvas, BorderLayout.CENTER);
    }

    /** Select drawing line action */
    AbstractAction selectDrawLine = new AbstractAction() {

        /** UID */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.setDrawingType(GeometricalObjectType.LINE);
        }
    };

    /** Select drawing circle action */
    AbstractAction selectDrawCircle = new AbstractAction() {

        /** UID */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.setDrawingType(GeometricalObjectType.CIRCLE);
        }
    };

    /** Select drawing filled circle action */
    AbstractAction selectDrawFCircle = new AbstractAction() {

        /** UID */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.setDrawingType(GeometricalObjectType.FCIRCLE);
        }
    };


    /**
     * Creates menu bar with all of its components.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        OpenAction open = new OpenAction(this, canvas, model, listModel);
        SaveAction save = new SaveAction(this, model);
        SaveAsAction saveAs = new SaveAsAction(this, model);
        ExportAction export = new ExportAction(this, model, canvas);

        save.putValue(Action.NAME, "Save");
        saveAs.putValue(Action.NAME, "Save As");
        open.putValue(Action.NAME, "Open");
        export.putValue(Action.NAME, "Export");

        file.add(save);
        file.add(saveAs);
        file.add(open);
        file.add(export);

        menuBar.add(file);

        setJMenuBar(menuBar);
    }

    /**
     * Creates toolbar and it's options.
     */
    private void createToolbar() {
        JToolBar toolBar = new JToolBar("Tools");
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        toolBar.add(backgroundColChooser);
        toolBar.add(foregroundColChooser);

        selectDrawLine.putValue(Action.NAME, "Line");
        selectDrawCircle.putValue(Action.NAME, "Circle");
        selectDrawFCircle.putValue(Action.NAME, "Filled circle");

        toolBar.add(new JButton(selectDrawLine));
        toolBar.add(new JButton(selectDrawCircle));
        toolBar.add(new JButton(selectDrawFCircle));

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    /**
     * Creates list of objects drawn onto canvas.
     */
    public void createList() {
        JList<GeometricalObject> objectList = new JList<>(listModel);

        objectList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = objectList.getSelectedIndex();
                    model.getObject(index).showChangeDialog(JVDraw.this);
                    objectList.clearSelection();
                    model.notifyObjectChange();
                }
            }
        });

        objectList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_DELETE == e.getKeyChar() && objectList.getSelectedIndex() != -1) {
                    int index = objectList.getSelectedIndex();
                    model.remove(model.getObject(index));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(objectList);
        scrollPane.setPreferredSize(new Dimension(180, 500));
        this.getContentPane().add(scrollPane, BorderLayout.EAST);
    }

    /**
     * Creates info bar
     */
    private void createInfoBar() {
        JToolBar infoBar = new JToolBar();

        JLabel colorInfoLabel = new JLabel(
                "Foreground color: " + foregroundColChooser.getColorString()
                        + ", background color: " + backgroundColChooser.getColorString() + ".");

        infoBar.add(colorInfoLabel);

        ColorChangeListener listener = (s, o, n) -> {
            colorInfoLabel.setText("Foreground color: " + foregroundColChooser.getColorString()
                    + ", background color: " + backgroundColChooser.getColorString() + ".");
        };

        backgroundColChooser.addColorChangeListener(listener);
        foregroundColChooser.addColorChangeListener(listener);

        this.getContentPane().add(infoBar, BorderLayout.PAGE_END);
    }

    /**
     * Entrance into {@link JVDraw}
     *
     * @param args No arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JVDraw().setVisible(true);
        });
    }
}
