package fr.inria.atlanmod.appa.exception;

public class NoSuchFieldException extends AppaException {

	private static final long serialVersionUID = 1L;

	public NoSuchFieldException() {
		super();
	}

	public NoSuchFieldException(String message) {
		super(message);
	}

	public NoSuchFieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchFieldException(Throwable cause) {
		super(cause);
	}

}
