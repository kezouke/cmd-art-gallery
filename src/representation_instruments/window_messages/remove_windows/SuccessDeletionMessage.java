package representation_instruments.window_messages.remove_windows;

public class SuccessDeletionMessage {
    public void outputMessage() {
        String successMessage = """
                You successfully removed it!
                """;
        System.out.println(successMessage);
    }
}
