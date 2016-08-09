package hr.fer.zemris.java.hw16.jvdraw.draw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Model for displaying list of objects stored in {@link DrawingModel}.
 * 
 * @author Domagoj Tokić
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> {

	/** UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Storage of {@link GeometricalObject} objects.
	 */
	private DrawingModel model;

	/**
	 * Creates a new DrawingObjectListModel with given model from which data is
	 * pulled.
	 * 
	 * @param model model from which data is pulled
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				fireIntervalRemoved(source, index0, index1);
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				fireContentsChanged(source, index0, index1);
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				fireIntervalAdded(source, index0, index1);
			}
		});
	}
	
	/**
	 * Sets model
	 * @param model Model
	 */
	public void setModel(DrawingModel model) {
		this.model = model;
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

}