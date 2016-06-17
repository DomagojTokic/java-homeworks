package hr.fer.zemris.java.hw14.dao;

/**
 * Exception for handling errors while accessing data.
 * 
 * @author Domagoj Tokić
 *
 */
public class DAOException extends RuntimeException {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates default {@link DAOException}
	 */
	public DAOException() {
	}

	/**
	 * Creates {@link DAOException} with additional options
	 * 
	 * @param message Exception message.
	 * @param cause Exception cause.
	 * @param enableSuppression If true, suppression is enabled.
	 * @param writableStackTrace If false, stack trace is not writable.
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Creates {@link DAOException} with message which is wrapper to some other
	 * exception.
	 * 
	 * @param message Exception message
	 * @param cause Exception cause.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates {@link DAOException} with message.
	 * 
	 * @param message Exception message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Creates {@link DAOException} which is wrapper to another exception.
	 * 
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}