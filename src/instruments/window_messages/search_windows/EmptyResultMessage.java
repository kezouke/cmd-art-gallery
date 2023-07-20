package instruments.window_messages.search_windows;

public class EmptyResultMessage {
    public void outputMessage() {
        String emptyResultMessage = """
                Unfortunately, your query did not return any results.
                Please try again.
                """;
        System.out.println(emptyResultMessage);
    }
}
