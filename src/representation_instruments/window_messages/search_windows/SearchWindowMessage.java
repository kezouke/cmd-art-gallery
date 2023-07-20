package representation_instruments.window_messages.search_windows;

public class SearchWindowMessage {
    public void outputGreetings() {
        new EnterAKeywordMessage().outputMessage();
    }

    public void outputEmptyResult() {
        new EmptyResultMessage().outputMessage();
    }
}
