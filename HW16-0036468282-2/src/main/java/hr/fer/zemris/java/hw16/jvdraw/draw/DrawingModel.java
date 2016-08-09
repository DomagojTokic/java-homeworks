package hr.fer.zemris.java.hw16.jvdraw.draw;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Interface for storage of {@link GeometricalObject} instances. Supports
 * {@link DrawingModelListener} listeners.
 * 
 * @author Domagoj Tokić
 *
 */
public interface DrawingModel {

	/**
	 * Returns number of {@link GeometricalObject} instances.
	 * 
	 * @return number of {@link GeometricalObject} instances.
	 */
	public int getSize();

	/**
	 * Returns {@link GeometricalObject} with given index.
	 * 
	 * @param index Index of {@link GeometricalObject}
	 * @return {@link GeometricalObject} with given index.
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds new {@link GeometricalObject} to model.
	 * @param object new {@link GeometricalObject}
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes {@link GeometricalObject} from model.
	 * @param object Object for removal
	 */
	public void remove(GeometricalObject object);

	/**
	 * Adds new {@link DrawingModelListener} to model.
	 * @param l New listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes {@link DrawingModelListener} from model.
	 * @param l Listener for removal
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Notifies listeners about changes made on objects.
	 */
	public void notifyObjectChange();

	/**
	 * Removes all objects from model.
     */
	public void clear();

}