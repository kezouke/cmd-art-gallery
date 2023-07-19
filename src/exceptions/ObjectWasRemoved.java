package exceptions;

public class ObjectWasRemoved extends RuntimeException {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ObjectWasRemoved() {
        super("You are trying to see removed object!");
    }
}
