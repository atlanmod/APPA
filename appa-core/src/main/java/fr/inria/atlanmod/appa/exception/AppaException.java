package fr.inria.atlanmod.appa.exception;

/**
 *	Generic APPA Exception
 */
public class AppaException extends Exception {

	private static final long serialVersionUID = 1L;

	public AppaException() {
		super();
	}

	public AppaException(String message) {
		super(message);
	}

	public AppaException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppaException(Throwable cause) {
		super(cause);
	}

}
