package fr.inria.atlanmod.appa.exception;

public class IncompatibleOperandeException extends AppaException {

	private static final long serialVersionUID = 1L;

	public IncompatibleOperandeException() {
		super();
	}

	public IncompatibleOperandeException(String message) {
		super(message);
	}

	public IncompatibleOperandeException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncompatibleOperandeException(Throwable cause) {
		super(cause);
	}

}
