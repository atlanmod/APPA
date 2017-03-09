package fr.inria.atlanmod.appa.exception;

/**
 * Indicates that the requested operation has failed
 */
public class OperationFailedException extends AppaException {

	private static final long serialVersionUID = 1L;

	public OperationFailedException() {
		super();
	}

	public OperationFailedException(String message) {
		super(message);
	}

	public OperationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperationFailedException(Throwable cause) {
		super(cause);
	}

}
