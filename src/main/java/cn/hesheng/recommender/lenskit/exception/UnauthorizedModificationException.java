package cn.hesheng.recommender.lenskit.exception;

public class UnauthorizedModificationException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnauthorizedModificationException() {
		super();
	}
	
	public UnauthorizedModificationException(String message) {
		super(message);
	}
	
	public UnauthorizedModificationException(Throwable cause) {
		super(cause);
	}
	
	public UnauthorizedModificationException(String message, Throwable cause) {
		super(message, cause);
	}
}
