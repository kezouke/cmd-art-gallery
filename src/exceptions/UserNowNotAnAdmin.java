package exceptions;

public class UserNowNotAnAdmin extends RuntimeException {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public UserNowNotAnAdmin() {
        super("Now you can't see this window");
    }
}
