package representation_instruments.window_messages;

public class WrongCommandMessage {
    private final String message;

    public WrongCommandMessage() {
        this.message = """
            !!!!!!!!!!!!!!!!!!!!!!!!!!
            No such command was found!
            """;
    }

    public void outputMessage() {
        System.out.println(message);
    }
}
