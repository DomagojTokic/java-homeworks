package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.Hashtable;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Layout manager for visualizing {@link Calculator} class. It uses simple
 * grid-like structure with default dimensions of 5 rows and 7 columns. 5 cells
 * at the beginning of first row are reserved for calculator display and it's on
 * coordinates (1,1). Adding {@link Component} at coordinates between (1,2) and
 * (1,5) is illegal and exception will be thrown.
 * 
 * @author Domagoj
 *
 */
public class CalcLayout implements LayoutManager2 {

	/** Number of rows in <code>CalcLayout</code> */
	private final static int ROW_SIZE = 5;

	/** Number of columns in <code>CalcLayout</code> */
	private final static int COLUMN_SIZE = 7;

	/** Row number of reserved part of row. */
	private final static int RESERVED_ROW_NUM = 1;

	/**
	 * Column number of start of reserved part. From RESERVED_START_COL to
	 * RESERVED_END_COL inclusively at row number RESERVED_ROW_NUM is illegal to
	 * insert component. This number is always higher than 1.
	 */
	private final static int RESERVED_START_COL = 2;

	/**
	 * Column number of end of reserved part. From RESERVED_START_COL to
	 * RESERVED_END_COL inclusively at row number RESERVED_ROW_NUM is illegal to
	 * insert component. RESERVED_END_COL is always higher or equal to
	 * RESERVED_START_COL.
	 */
	private final static int RESERVED_END_COL = 5;

	/**
	 * Array list of components. Components are stored by indices inside list so
	 * every component is stored at index column+row*(ROW_SIZE-1).
	 */
	private Hashtable<Component, RCPosition> positions;

	/** Number of empty pixels between each row and column. */
	private int spacing;


	/**
	 * Interface for component size getter.
	 * 
	 * @author Domagoj
	 *
	 */
	private interface SizeGetter {
		
		/**
		 * Returns size of component.
		 * @param component Component
		 * @return size of component.
		 */
		Dimension getSize(Component component);
	}
		
	/**
	 * Creates instance of <code>CalcLayout</code> with initial
	 * <code>rowSpacing</code> and <code>columnSpacing</code> values set to 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Creates instance of <code>CalcLayout</code> with given initial spacing
	 * value.
	 * 
	 * @param spacing The initial spacing value.
	 */
	public CalcLayout(int spacing) {
		super();
		positions = new Hashtable<>();
		this.spacing = spacing;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		addLayoutComponent(comp, name);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		positions.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		SizeGetter prefferedSizeGetter = c -> {
			return c.getPreferredSize();
		};
		return getRequestedSize(parent, prefferedSizeGetter);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		SizeGetter minimumSizeGetter = c -> {
			return c.getMinimumSize();
		};
		return getRequestedSize(parent, minimumSizeGetter);
	}


	/**
	 * Returns size of layout. Which size - minimum, maximum or preferred
	 * depends on instance of {@link SizeGetter}.
	 * 
	 * @param parent Parent container of layout
	 * @param sizeGetter Size getter
	 * @return Layout dimensions
	 */
	private Dimension getRequestedSize(Container parent, SizeGetter sizeGetter) {
		int width = 0;
		int height = 0;

		int[] widths = new int[ROW_SIZE];
		int[] heights = new int[COLUMN_SIZE];
		Dimension componentSize;

		for (Component component : parent.getComponents()) {
			RCPosition place = positions.get(component);
			if (place != null) {
				componentSize = sizeGetter.getSize(component);
				if (widths[place.getRow() - 1] < componentSize.getWidth()) {
					widths[place.getRow() - 1] = (int) componentSize.getWidth();
				}
				if (heights[place.getColumn() - 1] < componentSize.getHeight()) {
					heights[place.getColumn() - 1] = (int) componentSize.getHeight();
				}
			}
		}

		for (int i = 0; i < widths.length; i++) {
			width += widths[i];
		}
		for (int i = 0; i < heights.length; i++) {
			height += heights[i];
		}

		return new Dimension(width, height);
	}

	@Override
	public void layoutContainer(Container parent) {
		int width = parent.getWidth();
		int height = parent.getHeight();
		int x = 0;
		int y = 0;
		int compHeight = 0;
		int compWidth = 0;

		for (Component component : parent.getComponents()) {
			RCPosition place = positions.get(component);

			if (place != null) {
				compHeight = (height - (ROW_SIZE - 1) * spacing) / ROW_SIZE;

				if (place.getColumn() == RESERVED_START_COL - 1
						&& place.getRow() == RESERVED_ROW_NUM) {
					compWidth = ((width - (COLUMN_SIZE - 1) * spacing) / COLUMN_SIZE)
							* (RESERVED_END_COL - RESERVED_START_COL + 2);
					compWidth += (RESERVED_END_COL - RESERVED_START_COL + 1) * spacing;
				} else {
					compWidth = (width - (COLUMN_SIZE - 1) * spacing) / COLUMN_SIZE;
				}

				x = (place.getColumn() - 1) * (spacing + compWidth);
				y = (place.getRow() - 1) * (spacing + compHeight);

				setComponentBounds(component, x, y, compWidth, compHeight);
			}
		}
	}

	/**
	 * Checks components minimum and maximum dimensions before setting component
	 * size. Component size can't be below minimum or above maximum size.
	 * 
	 * @param component
	 * @param x
	 * @param y
	 * @param compWidth
	 * @param compHeight
	 */
	private void setComponentBounds(Component component, int x, int y, int compWidth,
			int compHeight) {
		int width;
		int height;

		if (compWidth < component.getMinimumSize().getWidth()) {
			width = (int) component.getMinimumSize().getWidth();
		} else if (compWidth > component.getMaximumSize().getWidth()) {
			width = (int) component.getMaximumSize().getWidth();
		} else {
			width = compWidth;
		}

		if (compHeight < component.getMinimumSize().getHeight()) {
			height = (int) component.getMinimumSize().getHeight();
		} else if (compHeight > component.getMaximumSize().getHeight()) {
			height = (int) component.getMaximumSize().getHeight();
		} else {
			height = compHeight;
		}

		component.setBounds(x, y, width, height);
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position = null;
		if (constraints instanceof String) {
			String[] constraint = ((String) constraints).split(",");
			if (constraint.length != 2) {
				throw new IllegalArgumentException("Invalid cell position format!");
			} else {
				try {
					int row = Integer.parseInt(constraint[0]);
					int column = Integer.parseInt(constraint[1]);
					position = new RCPosition(row, column);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Invalid cell position format!");
				}
			}
		} else if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Invalid component constraint object class!");
		}

		if (position.getRow() <= 0 || position.getColumn() <= 0 || position.getRow() > ROW_SIZE
				|| position.getColumn() > COLUMN_SIZE) {
			throw new IllegalArgumentException("Constraint values (" + position.getRow() + ", "
					+ position.getColumn() + ") are out of bounds!");
		}
		if (positions.get(comp) != null) {
			throw new IllegalArgumentException("Component at position (" + position.getRow() + ", "
					+ position.getColumn() + ") already exists");
		}
		if (position.getRow() == RESERVED_ROW_NUM && position.getColumn() >= RESERVED_START_COL
				&& position.getColumn() <= RESERVED_END_COL) {
			throw new IllegalArgumentException("Unable to add component to reserved layout cells!");
		}

		positions.put(comp, position);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return target.getSize();
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

}
