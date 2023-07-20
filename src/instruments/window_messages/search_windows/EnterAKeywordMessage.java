package instruments.window_messages.search_windows;

public class EnterAKeywordMessage {
    public void outputMessage() {
        String enterAKeywordMessage = """
                Please, enter a keyword for search
                     or enter 'close', if you want to exit from here
                """;
        System.out.println(enterAKeywordMessage);
    }
}
