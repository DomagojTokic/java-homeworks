package hr.fer.zemris.java.hw16.jvdraw.draw;

/**
 * Listener of changes made on {@link DrawingModel}.
 * 
 * @author Domagoj Tokić
 *
 */
public interface DrawingModelListener {

	/**
	 * Action performed when new objects is added.
	 * 
	 * @param source Listener subject.
	 * @param index0 Starting index of object which need to be checked.
	 * @param index1 Ending index of object which need to be checked.
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Action performed when objects are removed.
	 * 
	 * @param source Listener subject.
	 * @param index0 Starting index of object which need to be checked.
	 * @param index1 Ending index of object which need to be checked.
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Action performed when objects are changed.
	 * 
	 * @param source Listener subject.
	 * @param index0 Starting index of object which need to be checked.
	 * @param index1 Ending index of object which need to be checked.
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}