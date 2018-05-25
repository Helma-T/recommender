package cn.hesheng.recommender.lenskit.exception;

public class RequestContentTypeException extends Exception {
	private static final long serialVersionUID = 1L;

	public RequestContentTypeException() {
		super();
	}
	
	public RequestContentTypeException(String message) {
		super(message);
	}
	
	public RequestContentTypeException(Throwable cause) {
		super(cause);
	}
	
	public RequestContentTypeException(String message, Throwable cause) {
		super(message, cause);
	}
}