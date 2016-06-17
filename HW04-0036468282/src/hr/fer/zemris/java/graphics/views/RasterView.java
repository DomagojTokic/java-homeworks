package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Interface for classes which provide view of rasters.
 * 
 * @author Domagoj Tokić
 *
 */
public interface RasterView {

	/**
	 * Produces view of raster. Way of providing view is specified in classes
	 * which inherit this interface.
	 * 
	 * @param raster Instance of BWRaster
	 * @return Raster view or flag if method writes in output.
	 */
	Object produce(BWRaster raster);

}
