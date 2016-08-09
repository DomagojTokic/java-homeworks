package hr.fer.zemris.java.hw16.jvdraw.draw;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Implementation Of {@link DrawingModel}
 * 
 * @author Domagoj Tokić
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/** List storing all drawn objects */
	private List<GeometricalObject> objects;

	/** {@link DrawingModel} listeners */
	private List<DrawingModelListener> listeners;

	/**
	 * Creates new instance of {@link DrawingModelImpl}.
	 */
	public DrawingModelImpl() {
		objects = new LinkedList<>();
		listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= objects.size()) {
			throw new IndexOutOfBoundsException("unable to retrieve object with index " + index);
		}
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		Objects.requireNonNull(object, "Tried to add null GeometricalObject!");

		objects.add(object);

		for (DrawingModelListener listener : listeners) {
			listener.objectsAdded(this, objects.size() - 1, objects.size() - 1);
		}
	}

	@Override
	public void remove(GeometricalObject object) {
		Objects.requireNonNull(object, "Trying to remove null object!");

		objects.remove(object);

		for (DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, 0, objects.size() == 0 ? 0 : objects.size() - 1);
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Unable to add null as listener!");

		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Trying to remove null listener!");

		listeners.remove(l);
	}
	
	@Override
	public void notifyObjectChange() {
		for(DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, 0, objects.size() - 1);
		}
	}

	@Override
	public void clear() {
		objects.clear();
		GeometricalObject.restartCounters();
	}
}
