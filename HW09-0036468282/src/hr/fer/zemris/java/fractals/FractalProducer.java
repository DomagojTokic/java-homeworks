package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.complex.ComplexPolynomial;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Divides
 * 
 * @author Domagoj
 *
 */
public class FractalProducer implements IFractalProducer {

	/** Complex polynomial in rooted form */
	private ComplexRootedPolynomial rootedPolynomial;

	/** Number of threads for fractal picture calculation */
	private static final int NUM_OF_THREADS = 8;

	/**
	 * Creates instance of {@link FractalProducer}.
	 * 
	 * @param rootedPolynomial Complex polynomial in rooted form
	 */
	public FractalProducer(ComplexRootedPolynomial rootedPolynomial) {
		this.rootedPolynomial = rootedPolynomial;
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width,
			int height, long requestNo, IFractalResultObserver observer) {
		short[] data = new short[height * width];
		ExecutorService pool = Executors
				.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), (r) -> {
					Thread t = new Thread(r);
					t.setDaemon(true);
					return t;
				});
		List<Future<Void>> results = new ArrayList<>();

		for (int j = 0; j < NUM_OF_THREADS; j++) {
			int yStart = j * (height / NUM_OF_THREADS);
			int yEnd;
			if (j < NUM_OF_THREADS - 1) {
				yEnd = (j + 1) * (height / NUM_OF_THREADS) - 1;
			} else {
				yEnd = height - 1;
			}

			FractalJob job = new FractalJob(reMin, reMax, imMin, imMax, width, height, data, yStart,
					yEnd, rootedPolynomial);

			results.add(pool.submit(job));
		}

		for (Future<Void> result : results) {
			try {
				result.get(); // returning null
			} catch (InterruptedException | ExecutionException e) {
			}
		}

		ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
		observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
	}

}
