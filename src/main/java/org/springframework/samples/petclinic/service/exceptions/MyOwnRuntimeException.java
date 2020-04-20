
package org.springframework.samples.petclinic.service.exceptions;

public class MyOwnRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public MyOwnRuntimeException() {
		super();
	}
	public MyOwnRuntimeException(final String s) {
		super(s);
	}
	public MyOwnRuntimeException(final String s, final Throwable throwable) {
		super(s, throwable);
	}
	public MyOwnRuntimeException(final Throwable throwable) {
		super(throwable);
	}
}
