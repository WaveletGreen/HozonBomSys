package sql.redis;

public class HzDBException extends RuntimeException {

	private static final long serialVersionUID = -6800034415946664295L;

	public HzDBException(String message, Throwable cause) {
		super(message, cause);
	}

	public HzDBException(String message) {
		super(message);
	}
}
