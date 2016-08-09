package hr.fer.zemris.java.hw16.jvdraw.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectType;

/**
 * Component for displaying all {@link GeometricalObject}'s currently drawn.
 * 
 * @author Domagoj Tokić
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** UID */
	private static final long serialVersionUID = 1L;

	/** Instance of {@link GeometricalObject} that is currently been drawn. */
	private GeometricalObject current;

	/** Tells if user is currently in process of drawing an object */
	private boolean drawing;

	/** Type of {@link GeometricalObject} which is currently chosen. */
	private GeometricalObjectType drawingType;

	/** Container of {@link GeometricalObject} instances */
	private DrawingModel model;

	/**
	 * Creates new instance of {@link JDrawingCanvas}.
	 * 
	 * @param model Container of {@link GeometricalObject} instances.
	 * @param backgroundColorProvider
	 * @param foregroundColorProvider
	 */
	public JDrawingCanvas(DrawingModel model, IColorProvider backgroundColorProvider,
			IColorProvider foregroundColorProvider) {
		this.model = model;
		model.addDrawingModelListener(this);
		drawingType = GeometricalObjectType.LINE;

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!drawing) {
					current = GeometricalObject.createGeometricalObject(e.getX(), e.getY(),
							e.getX(),
							e.getY(), backgroundColorProvider.getCurrentColor(),
							foregroundColorProvider.getCurrentColor(), drawingType);
					add(current);
					drawing = true;
				} else {
					model.add(current);
					drawing = false;
				}
				repaint();
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (drawing) {
					current.changeEndPoint(e.getX(), e.getY());
					repaint();
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (current != null && drawing)
			current.paint(g);
		if (model.getSize() > 0) {
			for (int i = 0; i < model.getSize(); i++) {
				model.getObject(i).paint(g);
			}
		}

	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	/**
	 * Sets new drawing type.
	 * 
	 * @param drawingType Drawing type
	 */
	public void setDrawingType(GeometricalObjectType drawingType) {
		this.drawingType = drawingType;
	}
	
	/**
	 * Sets model
	 * @param model Model
	 */
	public void setModel(DrawingModel model) {
		this.model = model;
		repaint();
	}

	public DrawingModel getModel() {
		return model;
	}

}
